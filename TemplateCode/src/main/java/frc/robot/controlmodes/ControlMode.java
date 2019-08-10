package frc.robot.controlmodes;

import frc.robot.state.InputState;
import frc.robot.state.IntentState;
import frc.robot.state.SensorState;
import frc.robot.state.RobotState.Mode;

public interface ControlMode {

    public int getNumJoysticks();

    public IntentState processInputs(InputState inputs, SensorState sensors, Mode mode);

}
