package br.com.sicoob.cnv.gestao.testes.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.sicoob.cnv.gestao.util.DateUtil;

@RunWith(PowerMockRunner.class)
//@PrepareForTest({ DateUtil.class })
public class UtilTest {

	@Test
	public void parseTest() {
		Assert.assertNotNull(DateUtil.parse("2019-01-01"));
	}

	@Test
	public void parseTestException() {
		Assert.assertNull(DateUtil.parse("2019//0011AAS010"));
	}

	@Test
	public void plusTest() {
		final Date hj = new Date();
		Assert.assertNotNull(DateUtil.plus(hj, 1));
		Assert.assertNotNull(DateUtil.plus(hj, null));
	}

	@Test
	public void minusTest() {
		final Date hj = new Date();
		Assert.assertNotNull(DateUtil.minus(hj, 1));
		Assert.assertNotNull(DateUtil.minus(hj, null));
	}

	@Test
	public void parseDateTest() {
		Assert.assertNotNull(DateUtil.parseDate(new Date(), "DD/MM/YYYY"));
	}

	@Test
	public void isDomingoTest() {
		Assert.assertTrue(DateUtil.isDomingo(DateUtil.parse("2019-02-03")));
		Assert.assertFalse(DateUtil.isDomingo(DateUtil.parse("2019-02-02")));
	}

	@Test
	public void zerarHorasTest() {
		Assert.assertNotNull(DateUtil.zerarHoras(new Date()));
	}

	@Test
	public void isMenorHoraTest() {
		Assert.assertNotNull(DateUtil.isMenorIgualHora("23:59"));
	}

}
