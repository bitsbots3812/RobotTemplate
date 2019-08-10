package frc.robot.subsystems;

public class Ballistics {

    public enum PreferredShot {
        StraightShot, ArcShot;
    }

    public static double calculateAngle(double muzzleVelocity, double targetHeight, double muzzleHeight, double distance, PreferredShot shotType) {

        double height = targetHeight - muzzleHeight;

        double g = 9.80665;

        double angleOne = Math.toDegrees(Math.atan((Math.pow(muzzleVelocity, 2) + Math.sqrt(Math.pow(muzzleVelocity, 4) - g * (g * Math.pow(distance, 2) + 2 * height * Math.pow(muzzleVelocity, 2)))) / (g * distance)));

        double angleTwo = Math.toDegrees(Math.atan((Math.pow(muzzleVelocity, 2) - Math.sqrt(Math.pow(muzzleVelocity, 4) - g * (g * Math.pow(distance, 2) + 2 * height * Math.pow(muzzleVelocity, 2)))) / (g * distance)));

        if(Double.isNaN(angleOne) && Double.isNaN(angleTwo)) {

            return Double.NaN;

        }

        else if(Double.isNaN(angleOne)) {

            return angleTwo;

        }

        else if(Double.isNaN(angleTwo)){

            return angleOne;

        }

        else if(angleOne > 0 && angleTwo < 0) {

            return angleOne;

        }

        else if(angleOne < 0 && angleTwo > 0) {

            return angleTwo;

        }

        else if(shotType == PreferredShot.StraightShot) {

            return Math.min(angleOne, angleTwo);

        }

        else {

            return Math.max(angleOne, angleTwo);

        }

    }


}
