package frc.robot.state;

import java.io.Serializable;
import java.util.Arrays;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickState implements Serializable {

    private static final long serialVersionUID = 8186034871492353185L;

    public JoystickState() {

    }

    public JoystickState(Joystick joy) {

        try {

            axes = new double[joy.getAxisCount()];
            buttons = new boolean[joy.getButtonCount() + 1];

            for(int i=0; i<axes.length; i++) {
                axes[i] = joy.getRawAxis(i);
            }

            for(int i=1; i <= buttons.length; i++) {
                buttons[i] = joy.getRawButton(i);
            }

            validInput = true;

        }

        catch(Exception e) {

            axes = new double[10];
            buttons = new boolean[20];
            Arrays.fill(axes, 0);
            Arrays.fill(buttons, false);
            validInput = false;

        }

    }

    public double[] axes;

    public boolean[] buttons;

    public boolean validInput;

}
