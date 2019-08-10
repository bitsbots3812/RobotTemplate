package frc.robot.state;

import java.security.Permission;

public class StopExit {

    public static class ExitTrappedException extends SecurityException {

        private static final long serialVersionUID = -6399994111959834055L;

    }

    public static void forbidSystemExitCall() {
        final SecurityManager securityManager = new SecurityManager() {
            @Override
            public void checkPermission(Permission permission) {
                if (permission.getName().contains("exitVM")) {
                    throw new ExitTrappedException();
                }
            }
        };
        System.setSecurityManager(securityManager);
    }

}
