package frc.robot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import frc.robot.controlmodes.ControlMode;
import frc.robot.controlmodes.ExampleControlMode;
import frc.robot.state.IntentState;
import frc.robot.state.JoystickState;
import frc.robot.state.RobotState;
import frc.robot.state.RobotState.Mode;

public class DeployTests {

    @Test
    public void sampleTest() {
        assertEquals(true, true);
        assertTrue(true);
    }

    @Test
    public void ControlModeTest() {

        ControlMode cm = new ExampleControlMode();

        RobotState state = new RobotState();

        OI.init(cm);
        
        state.inputs = OI.getInputs();

        state.sensors = OI.getSensors();

        IntentState intent = cm.processInputs(state.inputs, state.sensors, Mode.Teleop);

        assertEquals(intent.driveSpeed, 0, 0);
        assertEquals(intent.driveRotation, 0, 0);

        state.inputs.joysticks = new JoystickState[1];

        state.inputs.joysticks[0] = new JoystickState();

        state.inputs.joysticks[0].axes = new double[2];

        state.inputs.joysticks[0].axes[0] = 1;

        state.inputs.joysticks[0].axes[1] = 0.5;

        intent = cm.processInputs(state.inputs, state.sensors, Mode.Teleop);

        assertEquals(intent.driveSpeed, 1, 0);

        assertEquals(intent.driveRotation, 0.5, 0);

    }

}