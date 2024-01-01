package com.sms.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.sms.pojo.TextMessage;

@Controller
public class SmsController {

	@RequestMapping(value = "/sendSms")
	public String sendsms(@RequestParam("mobile") String mobile, @RequestParam("text") String text, Model model) {
		System.out.println("Executing SmsController :: sendsms!!");
		System.out.println("Mobile Numbers :: " + mobile);
		System.out.println("Content to send :: " + text);

		// hit smsgatewayhub..

		StringBuilder url = new StringBuilder("https://www.smsgatewayhub.com/api/mt/SendSMS?");
		url.append("APIKey=").append("NLopEuFKSkCgRfXPYSbFkQ").append("&");
		url.append("senderid=").append("SMSTST").append("&");
		url.append("channel=").append("1").append("&");
		url.append("DCS=").append("0").append("&");
		url.append("flashsms=").append("0").append("&");
		url.append("number=").append(mobile).append("&");
		url.append("text=").append(text).append("&");
		url.append("route=").append("1").append("&");

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);//request object creation..
		ResponseEntity<String> result = rt.exchange(url.toString(), HttpMethod.GET, entity, String.class);

		String responseBody = result.getBody();
		System.out.println("url :: "   + url);
		System.out.println("Response from smsgatewayub " + result.getBody());
		Gson gson = new Gson();
		TextMessage textMessage = gson.fromJson(responseBody, TextMessage.class);

		model.addAttribute("msg", textMessage.getErrorMessage());
		return "home";

	}
	

	@RequestMapping(value = "/sendSmsByJson", method = RequestMethod.POST)
	public String sendSmsByJson(@RequestParam("mobile") String mobile, @RequestParam("text") String text, Model model) {

		RestTemplate rt = new RestTemplate();
		StringBuilder url = new StringBuilder("http://localhost:8080/messaging-service/sms/sendSmsService/");
		url.append(mobile).append("/").append(text);

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> result = rt.exchange(url.toString(), HttpMethod.POST, entity, String.class);
		System.out.println("Result is  : " + result.getBody());
		model.addAttribute("msg", result.getBody());

		return "home";

	}
	
	public static void main(String[] args) {
		StringBuilder url = new StringBuilder("https://www.smsgatewayhub.com/api/mt/SendSMS?");
		url.append("APIKey=").append("NLopEuFKSkCgRfXPYSbFkQ").append("&");
		url.append("senderid=").append("SMSTST").append("&");
		url.append("channel=").append("1").append("&");
		url.append("DCS=").append("0").append("&");
		url.append("flashsms=").append("0").append("&");
		url.append("number=").append("9676737076").append("&");
		url.append("text=").append("testing").append("&");
		url.append("route=").append("1").append("&");

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> result = rt.exchange(url.toString(), HttpMethod.GET, entity, String.class);
		System.out.println(url);
		System.out.println("Response from smsgatewayub " + result.getBody());
	}
}
