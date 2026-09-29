import org.junit.jupiter.api.Test;
import org.team1.app.CarVehicleFactory;
import org.team1.app.FileVehicleModel;
import org.team1.app.VehicleProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VehicleProcessorTest
{
    @Test
    void shouldCarSearchWithInvalidData()
    {
        FileVehicleModel model = new FileVehicleModel(new CarVehicleFactory());
        VehicleProcessor processor = new VehicleProcessor(model);
        processor.addVehicle("car", new String[]{"Lexus", "152", "279799"});
        processor.addVehicle("car", new String[]{"Citroen", "120", "46995"});
        processor.addVehicle("car", new String[]{"BMW", "133", "27222"});
        processor.addVehicle("car", new String[]{"Lada", "74", "78532"});
        processor.addVehicle("car", new String[]{"Nissan", "112", "152567"});

        int result = processor.getVehicleNumber("car", new String[]{"FakeModel", "112", "152567"});
        assertEquals(-1, result);
    }

    @Test
    void shouldCarSearchWhenListIsEmpty()
    {
        FileVehicleModel model = new FileVehicleModel(new CarVehicleFactory());
        VehicleProcessor processor = new VehicleProcessor(model);

        int result = processor.getVehicleNumber("car", new String[]{"Lexus", "152", "279799"});
        assertEquals(0, result);
    }

    @Test
    void shouldCarSearchWithOneMatch()
    {
        FileVehicleModel model = new FileVehicleModel(new CarVehicleFactory());
        VehicleProcessor processor = new VehicleProcessor(model);
        processor.addVehicle("car", new String[]{"Lexus", "152", "279799"});
        processor.addVehicle("car", new String[]{"Citroen", "120", "46995"});
        processor.addVehicle("car", new String[]{"BMW", "133", "27222"});
        processor.addVehicle("car", new String[]{"Lada", "74", "78532"});
        processor.addVehicle("car", new String[]{"Nissan", "112", "152567"});

        int result = processor.getVehicleNumber("car", new String[]{"BMW", "133", "27222"});
        assertEquals(1, result);
    }

    @Test
    void shouldCarSearchWithMultipleMatches()
    {
        FileVehicleModel model = new FileVehicleModel(new CarVehicleFactory());
        VehicleProcessor processor = new VehicleProcessor(model);
        processor.addVehicle("car", new String[]{"Nissan", "112", "152567"});
        processor.addVehicle("car", new String[]{"Lexus", "152", "279799"});
        processor.addVehicle("car", new String[]{"Citroen", "120", "46995"});
        processor.addVehicle("car", new String[]{"Nissan", "112", "152567"});
        processor.addVehicle("car", new String[]{"BMW", "133", "27222"});
        processor.addVehicle("car", new String[]{"Lada", "74", "78532"});
        processor.addVehicle("car", new String[]{"Nissan", "112", "152567"});

        int result = processor.getVehicleNumber("car", new String[]{"Nissan", "112", "152567"});
        assertEquals(3, result);
    }
}
