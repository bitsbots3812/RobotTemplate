package frc.robot.controlmodes;

import frc.robot.state.InputState;
import frc.robot.state.IntentState;
import frc.robot.state.SensorState;
import frc.robot.state.RobotState.Mode;

public class ExampleControlMode implements ControlMode {

    public int getNumJoysticks() {
        return 1;
    }

	public IntentState processInputs(InputState inputs, SensorState sensors, Mode mode) {

        if(mode == Mode.Teleop) {
        
            IntentState intent = new IntentState();

            intent.driveSpeed = inputs.joysticks[0].axes[0];

            intent.driveRotation = inputs.joysticks[0].axes[1];

            return intent;

        }

        else {

            IntentState intent = new IntentState();

            intent.driveSpeed = 0.7;

            intent.driveRotation = 0;

            return intent;

        }

    }
    
}
