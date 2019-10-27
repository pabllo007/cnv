package br.com.sicoob.cnv.gestao.testes.mensagem;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.sicoob.cnv.gestao.mensagens.EnumMsgProcValidar;

@RunWith(PowerMockRunner.class)
public class EnumMsgProcValidarTest {

	@Test
	public void enumMsgProcValidarTest() {
		final EnumMsgProcValidar[] values = EnumMsgProcValidar.values();
		for(final EnumMsgProcValidar enu : values) {
			Assert.assertTrue(EnumMsgProcValidar.recuperarMsg(enu.getRetornoProc()).equals(enu.getCodigo()));
		}

	}

}
