package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.controlmodes.ControlMode;
import frc.robot.controlmodes.ExampleControlMode;
import frc.robot.state.OutputState;
import frc.robot.state.RobotState;
import frc.robot.subsystems.BlackBox;

public class Robot extends TimedRobot {

  //Test Changes

  ControlMode cm;

  public void robotInit() {

    cm = new ExampleControlMode();

    OI.init(cm);
    BlackBox.init();

    clockSync();

    BlackBox.log("Robot initialized");
      
  }

  public void robotPeriodic() {

  }

  public void autonomousInit() {

    BlackBox.log("Autonomous has begun");

  }

  
  public void teleopInit() {

    BlackBox.log("Teleop has begun");

  }

  public void autonomousPeriodic() {

    RobotState state = new RobotState();

    state.currentMode = RobotState.Mode.Autonomous;

    bothPeriodic(state);
    
  }

  public void teleopPeriodic() {

    RobotState state = new RobotState();

    state.currentMode = RobotState.Mode.Teleop;

    bothPeriodic(state);

  }

  public void bothPeriodic(RobotState state) {

    state.inputs = OI.getInputs();

    state.sensors = OI.getSensors();

    state.intent = cm.processInputs(state.inputs, state.sensors, state.currentMode);

    state.outputs = new OutputState(state.intent);

    sendOutputs(state.outputs);

    BlackBox.logState(state);

  }

  private void sendOutputs(OutputState outputs) {

  }

  public void disabledInit() {

    BlackBox.log("Robot has been disabled");

  }

  public void disabledPeriodic() {

  }

  public void testInit() {

  }

  public void testPeriodic() {

  }

  private void clockSync() {

    while(!DriverStation.getInstance().isDSAttached()) {
      try {
          Thread.sleep(20);
      } catch (InterruptedException e) {
          BlackBox.log("Failed to get driver station connection - clock sync failed");
          return;
      }
    }

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e1) {
        BlackBox.log("An unknown error occured - clock sync failed");
        return;
    }

  }

}
