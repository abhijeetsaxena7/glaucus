package com.galucus.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galucus.assignment.config.TransactionProperties;
import com.galucus.assignment.model.NumberModel;
import com.galucus.assignment.service.NumberService;

@RestController
@RequestMapping("/number")
public class NumberController {
	private NumberService numberService;
	private TransactionProperties transactionProperties;

	@Autowired	
	public NumberController(NumberService numberService, TransactionProperties transactionProperties) {
		this.numberService = numberService;
		this.transactionProperties = transactionProperties;
	}

	/**
	 * This method accepts the key for the row and updates the value.<br>
	 * If transaction lock is not acquired, then it retries the number of transaction given in application.properties.<br>
	 * If number of retries are exceeded, {@link HttpStatus} serviceUnavailable status is returned.<br>
	 * 
	 * @param id
	 * @return httpstatus 200 if sucess and number model, else httpstatus 503
	 */
	@PostMapping("/{id}")
	public ResponseEntity<NumberModel> updateNumberMessage(@PathVariable int id) {
		NumberModel numberModel=null;
		int retry=0;
		boolean isError=false;
		ResponseEntity<NumberModel> responseEntity;
		while (retry<transactionProperties.getRetries()) {
			try {
				numberModel = numberService.addUpdateNumber(id);
				break;
			} catch (Exception e) {
				retry++;
				if(retry==transactionProperties.getRetries()) {
					isError = true;
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					isError = true;
					break;
					
				}
			}
		}
		
		if(isError) {
			responseEntity = new ResponseEntity<NumberModel>(HttpStatus.SERVICE_UNAVAILABLE);
		}else {
			responseEntity = new ResponseEntity<NumberModel>(numberModel,HttpStatus.OK);
		}
		return responseEntity;
	}

}
