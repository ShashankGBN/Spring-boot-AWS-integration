package com.javatechie.aws.sqs.controller;

import java.lang.System.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsSQSController {

//  Logger logger= LoggerFactory.getLogger(SpringbootAwsSqsExeApplication.class);

  @Autowired
  private QueueMessagingTemplate queueMessagingTemplate;

  @Value("${cloud.aws.end-point.uri}")
  private String endpoint;

  @GetMapping("/send/{message}")
  public void sendMessageToQueue(@PathVariable String message) {
      queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
  }

  @SqsListener("DemoQueue")
  public void loadMessageFromSQS(String message)  {
	  System.out.println(message);
//      logger.info("message from SQS Queue {}",message);
  }


	
}
