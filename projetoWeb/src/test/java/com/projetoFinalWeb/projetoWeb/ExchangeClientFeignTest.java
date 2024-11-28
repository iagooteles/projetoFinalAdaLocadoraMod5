package com.projetoFinalWeb.projetoWeb;

import com.projetoFinalWeb.projetoWeb.apis.ExchangeClientFeign;
import com.projetoFinalWeb.projetoWeb.apis.ExchangeResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExchangeClientFeignTest {

    @Mock
    private ExchangeClientFeign exchangeClientFeign;

    @Mock
    private ExchangeClientFeign exchangeClientFeignMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExchangeClientFeign() {
        ExchangeResponseDTO responseDTO = new ExchangeResponseDTO();
        ExchangeResponseDTO.ExchangeData exchangeData = new ExchangeResponseDTO.ExchangeData();
        exchangeData.setBid("5.24");
        exchangeData.setAsk("5.30");
        responseDTO.setUsdBrl(exchangeData);

        when(exchangeClientFeignMock.getExchange()).thenReturn(responseDTO);

        var response = exchangeClientFeignMock.getExchange();

        assertNotNull(response);
        assertNotNull(response.getUsdBrl());
        assertEquals("5.24", response.getUsdBrl().getBid());
        assertEquals("5.30", response.getUsdBrl().getAsk());

        verify(exchangeClientFeignMock, times(1)).getExchange();
    }
}