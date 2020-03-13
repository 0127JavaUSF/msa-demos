package com.revature.listeners;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.BookService;

@Component
public class SQSListener implements InitializingBean {

	@Value("${SNS_ACCESS_KEY}")
	private String accessKey;
	
	@Value("${SNS_SECRET_ACCESS_KEY}")
	private String secretAccessKey;
	
	@Value("${SNS_REGION}")
	private String region;
	
	@Value("${SQS_URL}")
	private String sqsUrl;
	
	Logger logger = Logger.getLogger(SQSListener.class);
	
	ObjectMapper om;
	BookService bookService;
	
	private BasicAWSCredentials credentials;
	private AmazonSQS sqsClient;
	
	@Autowired
	public SQSListener(ObjectMapper om, BookService bookService) {
		super();
		this.om = om;
		this.bookService = bookService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
		sqsClient = AmazonSQSClient.builder()
						.withCredentials(new AWSStaticCredentialsProvider(credentials))
						.withRegion(region)
						.build();
	}
	
	/**
	 * Method is scheduled - it will execute repeatedly every 15 seconds.
	 * This way we can continuously poll SQS for new messages and response
	 * accordingly.
	 */
	@Scheduled(fixedRate = 15000)
	public void pollQueue() {
		ReceiveMessageResult pollResult = getMessages();
		List<Message> messages = pollResult.getMessages();
		logger.info("Message received");
		Comparator<String> myComparator = (s1, s2) -> 1;
		
		
		/**
		 * Lambda functions in Java implicitly implement a functional interface
		 */
		messages.forEach(message -> {
			String body = message.getBody();
			BookDeletionRequest deletionRequest = null;
			
			logger.info("Processing message: " + body);
			
			try {
				deletionRequest = om.readValue(body, BookDeletionRequest.class);
				bookService.deleteBooksByAuthorId(deletionRequest.getAuthorId());
				deleteMessage(message.getReceiptHandle());
			} catch(IOException e) {
				e.printStackTrace();
			}
		});

	}
	
	
	/**
	 * Will send a message to SQS and read messages.
	 * Messages are given a 15 second visibility timeout so that our service
	 * has exclusive access to those messages for 15 seconds - which is a very
	 * conservative amount of time for processing the request. If SQS receives 
	 * no response then it is free to send the message to a different service
	 * (or potentially this one again) after 15 seconds.
	 * @return
	 */
	public ReceiveMessageResult getMessages() {
		ReceiveMessageRequest messageRequest = new ReceiveMessageRequest(sqsUrl);
		messageRequest.setVisibilityTimeout(15);
		ReceiveMessageResult result = sqsClient.receiveMessage(messageRequest);
		return result;
	}
	
	/**
	 * Will delete the message read from SQS with a provide receiptHandle.
	 * This should be called once the processing of the request is complete.
	 */
	public void deleteMessage(String receiptHandle) {
		DeleteMessageRequest deleteRequest = 
				new DeleteMessageRequest(sqsUrl, receiptHandle);
		sqsClient.deleteMessage(deleteRequest);
	}
}

class BookDeletionRequest {
	private int authorId;

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public BookDeletionRequest(int authorId) {
		super();
		this.authorId = authorId;
	}

	public BookDeletionRequest() {
		super();
	}
}
