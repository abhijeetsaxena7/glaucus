package com.galucus.assignment.service;

import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.galucus.assignment.entity.NumberTbl;
import com.galucus.assignment.model.NumberModel;
import com.galucus.assignment.repo.NumberRepo;

@Service
public class NumberService {
	private NumberRepo numberRepo;

	@Autowired
	public NumberService(NumberRepo numberRepo) {
		this.numberRepo = numberRepo;
	}

	/**
	 * This method is used to update value field in {@link NumberTbl} for the given id.<br>
	 * This method acquires lock on the transaction and releases it when data is commited.<br>
	 * This allows for consistent updates.<br>
	 * @param id
	 * @return {@link NumberModel}
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public NumberModel addUpdateNumber(int id) {
			Optional<NumberTbl> numberOpt = numberRepo.findById(id);
			NumberTbl numberTbl;
			if (numberOpt.isPresent()) {
				numberTbl = numberOpt.get();
			} else {
				numberTbl = new NumberTbl();
			}
			numberTbl.setValue(numberTbl.getValue() + 1);
			numberRepo.save(numberTbl);
			NumberModel numberModel = new NumberModel();
			BeanUtils.copyProperties(numberTbl, numberModel);
		return numberModel;
	}
}
