package br.com.nt_inn.hotel_api.modules.hotel.service;

import br.com.nt_inn.hotel_api.config.exception.ValidationException;
import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelRequest;
import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelResponse;
import br.com.nt_inn.hotel_api.modules.hotel.model.Hotel;
import br.com.nt_inn.hotel_api.modules.hotel.repository.HotelRepository;
import br.com.nt_inn.hotel_api.modules.hotel.validation.HotelValidator;
import br.com.nt_inn.hotel_api.modules.hotel.validation.HotelIdValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HotelServiceTest {

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelValidator hotelValidator;

    @Mock
    private HotelIdValidator idValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveHotel_Success() {
        HotelRequest request = new HotelRequest();
        request.setNome("Hotel Teste");
        request.setPais("Brasil");
        request.setCidade("São Paulo");
        request.setQtdEstrelas(5);

        Hotel hotel = new Hotel(1L, "Hotel Teste", "Brasil", "São Paulo", 5, 0, List.of(), List.of());
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        HotelResponse response = hotelService.save(request);

        assertNotNull(response);
        assertEquals("Hotel Teste", response.getNome());
        assertEquals("Brasil", response.getPais());
        assertEquals("São Paulo", response.getCidade());
        assertEquals(5, response.getQtdEstrelas());

        verify(hotelRepository, times(1)).save(any(Hotel.class));
        verify(hotelValidator, times(1)).validate(request);
    }

    @Test
    void testFindById_Success() {
        Hotel hotel = new Hotel(1L, "Hotel Teste", "Brasil", "São Paulo", 5, 0, List.of(), List.of());

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Hotel Teste", result.getNome());

        verify(hotelRepository, times(1)).findById(1L);
        verify(idValidator, times(1)).validate(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            hotelService.findById(1L);
        });

        assertEquals("Hotel não encontrado!", exception.getMessage());
        verify(idValidator, times(1)).validate(1L);
    }

    @Test
    void testFindAll_Success() {
        Hotel hotel1 = new Hotel(1L, "Hotel A", "Brasil", "São Paulo", 5, 0, List.of(), List.of());
        Hotel hotel2 = new Hotel(2L, "Hotel B", "Brasil", "Rio de Janeiro", 4, 0, List.of(), List.of());

        when(hotelRepository.findAll()).thenReturn(List.of(hotel1, hotel2));

        List<HotelResponse> result = hotelService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hotel A", result.get(0).getNome());
        assertEquals("Hotel B", result.get(1).getNome());

        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void testFindByNomeIgnoreCaseContaining_Success() {
        Hotel hotel = new Hotel(1L, "Hotel Teste", "Brasil", "São Paulo", 5, 0, List.of(), List.of());

        when(hotelRepository.findByNomeIgnoreCaseContaining("Hotel Teste")).thenReturn(List.of(hotel));

        List<HotelResponse> result = hotelService.findByNomeIgnoreCaseContaining("Hotel Teste");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hotel Teste", result.get(0).getNome());

        verify(hotelRepository, times(1)).findByNomeIgnoreCaseContaining("Hotel Teste");
    }

    @Test
    void testFindByPaisIgnoreCaseContaining_Success() {
        Hotel hotel = new Hotel(1L, "Hotel Teste", "Brasil", "São Paulo", 5, 0, List.of(), List.of());

        when(hotelRepository.findByPaisIgnoreCaseContaining("Brasil")).thenReturn(List.of(hotel));

        List<HotelResponse> result = hotelService.findByPaisIgnoreCaseContaining("Brasil");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hotel Teste", result.get(0).getNome());

        verify(hotelRepository, times(1)).findByPaisIgnoreCaseContaining("Brasil");
    }

    @Test
    void testFindByCidadeIgnoreCaseContaining_Success() {
        Hotel hotel = new Hotel(1L, "Hotel Teste", "Brasil", "São Paulo", 5, 0, List.of(), List.of());

        when(hotelRepository.findByCidadeIgnoreCaseContaining("São Paulo")).thenReturn(List.of(hotel));

        List<HotelResponse> result = hotelService.findByCidadeIgnoreCaseContaining("São Paulo");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hotel Teste", result.get(0).getNome());

        verify(hotelRepository, times(1)).findByCidadeIgnoreCaseContaining("São Paulo");
    }

    @Test
    void testFindByQtdEstrelas_Success() {
        Hotel hotel = new Hotel(1L, "Hotel Teste", "Brasil", "São Paulo", 5, 0, List.of(), List.of());

        when(hotelRepository.findByQtdEstrelas(5)).thenReturn(List.of(hotel));

        List<HotelResponse> result = hotelService.findByQtdEstrelas(5);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hotel Teste", result.get(0).getNome());

        verify(hotelRepository, times(1)).findByQtdEstrelas(5);
    }

    @Test
    void testFindByNomeIgnoreCaseContaining_ThrowsException_WhenNomeIsEmpty() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            hotelService.findByNomeIgnoreCaseContaining("");
        });

        assertEquals("Nome do hotel não informado!", exception.getMessage());
    }

    @Test
    void testFindByPaisIgnoreCaseContaining_ThrowsException_WhenPaisIsEmpty() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            hotelService.findByPaisIgnoreCaseContaining("");
        });

        assertEquals("País não informado!", exception.getMessage());
    }

    @Test
    void testFindByCidadeIgnoreCaseContaining_ThrowsException_WhenCidadeIsEmpty() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            hotelService.findByCidadeIgnoreCaseContaining("");
        });

        assertEquals("Cidade não informada!", exception.getMessage());
    }

    @Test
    void testFindByQtdEstrelas_ThrowsException_WhenQtdEstrelasIsNegative() {
        Integer qtdEstrelasNegativa = -1;

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            hotelService.findByQtdEstrelas(qtdEstrelasNegativa);
        });

        assertEquals("Quantidade de estrelas deve ser entre 0 e 5!", exception.getMessage());
    }

}
