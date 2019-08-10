package frc.robot.state;

import java.io.Serializable;

public class RobotState implements Serializable {

    public enum Mode {

        Teleop, Autonomous, Test, Disabled

    }

    private static final long serialVersionUID = -7265470711702197734L;
    public Mode currentMode;
    public InputState inputs;
    public SensorState sensors;
    public IntentState intent;
    public OutputState outputs;

}