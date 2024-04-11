package Test;

import Backend.Mediator.SHHComponent;
import Backend.Mediator.SmartHomeMediator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class SmartHomeMediatorTest {

    private SmartHomeMediator mediator;

    @Mock
    private SHHComponent mockShh;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mediator = new SmartHomeMediator();
        mediator.setSHH(mockShh);  //injecting the mocked SHHComponent
    }

    @Test
    void shouldBlockDoorsAndWindowsWhenAway() {
        mediator.notify(true);

        verify(mockShh, times(1)).blockDoorsAndWindows();
    }

    @Test
    void shouldUnblockDoorsAndWindowsWhenNotAway() {
        mediator.notify(false);

        verify(mockShh, times(1)).unblockDoorsAndWindows();
    }
}
