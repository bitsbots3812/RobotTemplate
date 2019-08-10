package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.controlmodes.ControlMode;
import frc.robot.state.InputState;
import frc.robot.state.JoystickState;
import frc.robot.state.SensorState;

public class OI {

    private static Joystick[] sticks;

    public static void init(ControlMode cm) {

        sticks = new Joystick[cm.getNumJoysticks()];

        for(int i=0; i<sticks.length; i++) {

            sticks[i] = new Joystick(i);

        }

    }

    public static InputState getInputs() {

        InputState inputs = new InputState();

        inputs.joysticks = getJoystickStates();

        return inputs;

    }

    private static JoystickState[] getJoystickStates() {

        JoystickState[] joysticks = new JoystickState[sticks.length];

        for(int i=0; i < sticks.length; i++) {

            joysticks[i] = new JoystickState(sticks[i]);
            
        }

        return joysticks;

    }

	public static SensorState getSensors() {
        
        SensorState sensors = new SensorState();

        return sensors;

	}

}
