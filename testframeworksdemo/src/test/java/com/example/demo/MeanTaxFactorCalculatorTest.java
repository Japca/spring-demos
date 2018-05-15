package com.example.demo;

import com.example.demo.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;


/**
 * Created by Jakub Krhovják on 5/8/18.
 */

class TaxFactorCalculator {

	public double calculateSum(double taxFactorOne, double taxFactorTwo) {
		return taxFactorOne + taxFactorTwo;
	}

}

class  TaxService {

	public double getCurrentTaxFactorFor(Person person) {
		return Math.random();
	}

	public String getInternalRevenueServiceAddress(String string) {
		return "";
	}

}

class MeanTaxFactorCalculator {

	private final TaxService taxService;

	public MeanTaxFactorCalculator(TaxService taxService) {
		this.taxService = taxService;
	}

	public double calculateMeanTaxFactorFor(Person person) {
		double currentTaxFactor = taxService.getCurrentTaxFactorFor(person);
		double anotherTaxFactor = taxService.getCurrentTaxFactorFor(person);
		return (currentTaxFactor + anotherTaxFactor) / 2;
	}

}

class TaxFactorInformationProvider {

	private final TaxService taxService;

	public TaxFactorInformationProvider(TaxService taxService) {
		this.taxService = taxService;
	}

	public String formatIrsAddress(Person person) {
		String irsAddress = taxService.getInternalRevenueServiceAddress(person.getCountryName());
		return "IRS:[" + irsAddress + "]";
	}



@RunWith(MockitoJUnitRunner.class)
public class MeanTaxFactorCalculatorTest {

	static final double TAX_FACTOR = 10;

	//	@Mock(answer = Answers.RETURNS_SMART_NULLS, extraInterfaces = {Iterable.class, Serializable.class}, name = "Custom tax service mock")
	TaxService taxService = mock(TaxService.class, withSettings().serializable());

	MeanTaxFactorCalculator systemUnderTest = new MeanTaxFactorCalculator(taxService);

	@Test
	public void should_calculate_mean_tax_factor() {
		// given
		given(taxService.getCurrentTaxFactorFor(any(Person.class))).willReturn(TAX_FACTOR);

		// when
		double meanTaxFactor = systemUnderTest.calculateMeanTaxFactorFor(new Person());

		// then
		then(meanTaxFactor).isEqualTo(TAX_FACTOR);
		then(taxService).isInstanceOf(Serializable.class);
	}
}

//	static final double TAX_FACTOR = 10;

//	@Mock
//	TaxService taxService;
//
//	@InjectMocks
//	MeanTaxFactorCalculator systemUnderTest;
//
//	@Test
//	public void should_calculate_mean_tax_factor() {
//		// given
//		given(taxService.getCurrentTaxFactorFor(any(Person.class))).willReturn(TAX_FACTOR);
//
//		// when
//		double meanTaxFactor = systemUnderTest.calculateMeanTaxFactorFor(new Person());
//
//		// then
//		then(meanTaxFactor).isEqualTo(TAX_FACTOR);
//	}


//
//	@Mock(answer = Answers.RETURNS_SMART_NULLS) TaxService taxService;
//
//	@InjectMocks
//	TaxFactorInformationProvider systemUnderTest;
//
//	@Test
//	public void should_calculate_mean_tax_factor() {
//		// when
//		String parsedIrsAddress = systemUnderTest.formatIrsAddress(new Person());
//
//		// then
//		then(parsedIrsAddress).isEqualTo("IRS:[]");
//	}


}
