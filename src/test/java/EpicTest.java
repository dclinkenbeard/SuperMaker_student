import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EpicTest {

  Epic magneto;
  Epic supergirl;

  Epic overPower;

  Power flight;
  Power superStrength;
  Power magnetism;

  @BeforeEach
  void setUp() {
    magneto = new Epic();
    supergirl = new Epic();

    overPower = new Epic();

    PowerFactory.addPower(new PowerFlight());
    PowerFactory.addPower(new PowerMagnetism());
    PowerFactory.addPower(new PowerSuperStrength());
  }

  @AfterEach
  void tearDown() {
    magneto = null;
    supergirl = null;

    overPower = null;

    flight = null;
    superStrength = null;
    magnetism = null;

    PowerFactory.ALL_POWERS.clear();

  }

  @Test
  void getNameTest(){
    magnetism = PowerFactory.getPower(PowerFactory.MAGNETISM);
    magneto.mutate(magnetism);
    assertEquals(Power.MAGNETO, magneto.getName());

    flight = PowerFactory.getPower(PowerFactory.FLIGHT);
    superStrength = PowerFactory.getPower(PowerFactory.SUPER_STRENGTH);

    supergirl.mutate(flight);
    supergirl.mutate(superStrength);
    assertEquals(Power.SUPERGIRL, supergirl.getName());

  }

  @Test
  void flightTest(){
    overPower.mutate(PowerFactory.getPower(PowerFactory.FLIGHT));
    overPower.setName("Flyer");
    assertEquals(Power.ALREADY_DEACTIVATE, overPower.deactivatePower());
    assertEquals(Power.POWER_ACTIVATED, overPower.activatePower());
    assertEquals(Power.ALREADY_ACTIVE, overPower.activatePower());
    assertEquals(Power.POWER_DEACTIVATED, overPower.deactivatePower());
  }

  @Test
  void magnetTest(){
    overPower.mutate(PowerFactory.getPower(PowerFactory.MAGNETISM));
    assertEquals(Power.ALREADY_DEACTIVATE, overPower.deactivatePower());
    assertEquals(Power.POWER_ACTIVATED, overPower.activatePower());
    assertEquals(Power.ALREADY_ACTIVE, overPower.activatePower());
    assertEquals(Power.POWER_DEACTIVATED, overPower.deactivatePower());
  }

  @Test
  void strengthTest(){
    double strength = 100.0;
    overPower.setStrength(strength);
    overPower.setName("StrongBad");
    assertEquals(strength, overPower.getStrength());
    overPower.mutate(PowerFactory.getPower(PowerFactory.SUPER_STRENGTH));
    assertEquals(Power.ALREADY_DEACTIVATE, overPower.deactivatePower());
    assertEquals(Power.POWER_ACTIVATED, overPower.activatePower());
    assertNotEquals(strength, overPower.getStrength());
    double originalStrength = strength;
    strength = strength * PowerSuperStrength.STRENGTH_MULTIPLIER;
    assertEquals(strength, overPower.getStrength());
    assertEquals(Power.POWER_ACTIVATED, overPower.activatePower());
    strength = strength * PowerSuperStrength.STRENGTH_MULTIPLIER;
    assertEquals(strength, overPower.getStrength());
    assertEquals(Power.POWER_DEACTIVATED, overPower.deactivatePower());
    assertEquals(originalStrength, overPower.getStrength());
  }

  @Test
  void testMutate() {
    magnetism = PowerFactory.getPower(PowerFactory.MAGNETISM);
    assertEquals(Power.NEW_POWER, magneto.mutate(magnetism));
    assertEquals(Power.DUPLICATE_POWER, magneto.mutate(magnetism));

    flight = PowerFactory.getPower(PowerFactory.FLIGHT);
    superStrength = PowerFactory.getPower(PowerFactory.SUPER_STRENGTH);

    assertEquals(0,overPower.listPower());
    overPower.mutate(flight);
    overPower.mutate(superStrength);
    assertEquals(Power.REPLACEMENT_POWER, overPower.mutate(magnetism));
    assertEquals(Power.LIMIT,overPower.listPower());

  }

  @Test
  void getterSetterTest(){
    String normieName = "dude";
    overPower.setName(normieName);
    assertEquals(normieName, overPower.getName());
    overPower.mutate(PowerFactory.getPower(PowerFactory.MAGNETISM));
    assertEquals(Power.MAGNETO, overPower.getName());

    double agility = 100.0;
    overPower.setAgility(agility);
    assertEquals(agility, overPower.getAgility());

    double durability = 100.0;
    overPower.setDurability(durability);
    assertEquals(durability, overPower.getDurability());

    double strength = 100.0;
    overPower.setStrength(strength);
    assertEquals(strength, overPower.getStrength());

  }

  @Test
  void equality() {
    overPower.setAgility(500.0);
    overPower.setDurability(500.0);
    magneto.setAgility(50.0);
    magneto.setDurability(200.0);
    supergirl.setAgility(500.0);
    supergirl.setDurability(500.0);
    assertNotEquals(overPower, magneto);
    assertEquals(overPower, supergirl);
    supergirl.mutate(PowerFactory.getPower(PowerFactory.FLIGHT));
    supergirl.mutate(PowerFactory.getPower(PowerFactory.SUPER_STRENGTH));
    assertNotEquals(overPower, supergirl);
  }

  @Test
  void randomPowerTest(){
    Power randomPower = PowerFactory.getRandomPower();
    assertNotNull(randomPower);

    overPower.mutate(randomPower);

    for (int i = 0; i < 10; i++){
      overPower.mutate();
    }

    assertEquals(Power.LIMIT, overPower.listPower());

    overPower.mutate(PowerFactory.getPower("cake"));


  }
}