package com.revature.messengers;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SNSMessenger implements InitializingBean {
	
	@Value("${SNS_ACCESS_KEY}")
	private String accessKey;
	
	@Value("${SNS_SECRET_ACCESS_KEY}")
	private String secretAccessKey;
	
	@Value("${SNS_REGION}")
	private String region;
	
	@Value("${SNS_TOPIC_ARN}")
	private String topicArn;
	
	@Autowired
	ObjectMapper objectMapper;
	
	Logger logger = Logger.getLogger(SNSMessenger.class);
	
	private BasicAWSCredentials credentials;
	private AmazonSNS snsClient;
	
	/**
	 * We should configure our AWS credentials in this step as otherwise
	 * the credentials will not be setup when we attempt to use them.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
		snsClient = AmazonSNSClient.builder()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(region)
					.build();
		
	}
	
	public void sendDeleteBooksMessage(int authorId) {
		// Create JSON data
		String json = null;
		// Creating JSON String
		try {
			json = objectMapper
					.writeValueAsString(new BookDeletionRequest(authorId));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// Instantiating a 'request' that can be sent out
		PublishRequest request = new PublishRequest(topicArn, json);
		
		// Sending a request, which provides a response
		PublishResult result = snsClient.publish(request);
		logger.info("Sending message: " + result.getMessageId());
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
	
}