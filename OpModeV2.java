package org.firstinspires.ftc.teamcode;



        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.ElapsedTime;
        import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="OpModeV2", group="Linear Opmode")

public class OpModeV2 extends LinearOpMode {

        // Declare OpMode members.
        private ElapsedTime runtime = new ElapsedTime();

        private Servo clawServo1 = null;
        private Servo clawServo2 = null;
        private Servo tailServo = null;
        private DcMotor leftDrive = null;
        private DcMotor rightDrive = null;
        private DcMotor forkliftDrive = null;


    // All variables needed for Servos
    private boolean servoDirection = true;
    private boolean buttonPress = true;
    private double servoPos = 0;
    private boolean moveBackOrFront = true;
    private boolean UpOrDown = true;
    private int loop = 0;
    private boolean moveUpOrDown = true;
    int InsideLoopUp = 0;
    int InsideLoopDown = 0;
    double speed;
    int whichLoop;
    int servo1;
    int servo2;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();



        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        clawServo1 = hardwareMap.get(Servo.class, "clawServo1");
        clawServo2 = hardwareMap.get(Servo.class, "clawServo2");
        tailServo = hardwareMap.get(Servo.class, "tailServo");
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        forkliftDrive = hardwareMap.get(DcMotor.class, "forklift_drive");
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        servoPos = clawServo1.getPosition();

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);



        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            ++loop;
            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double leftPowerPrev=0;
            double rightPower;
            double rightPowerPrev;


            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.
            //if(debugToggle.getState() == true) {
            //    debugToggle.setState(false);
            //}
            //else {
            //    debugToggle.setState(true);
            //}

            buttonPress = gamepad1.a;
            if(gamepad1.a == true && moveBackOrFront == true) {
                clawServo1.setDirection(Servo.Direction.REVERSE);
                clawServo1.setPosition(0.46);
                clawServo2.setDirection(Servo.Direction.FORWARD);
                clawServo2.setPosition(0.35);
                moveBackOrFront = false;
                whichLoop = 1;
                while(gamepad1.a == true) {

                }

            } else if (gamepad1.a == true && moveBackOrFront == false ) {


                clawServo1.setDirection(Servo.Direction.FORWARD);
                clawServo1.setPosition(0.35);
                clawServo2.setDirection(Servo.Direction.REVERSE);
                clawServo2.setPosition(0.4);
                moveBackOrFront = true;
                whichLoop = 2;
                while(gamepad1.a == true) {

                }
            }




            if(gamepad1.b == true && UpOrDown == true) {
                tailServo.setDirection(Servo.Direction.FORWARD);
                tailServo.setPosition(0.70);
                UpOrDown = false;
            } else if(gamepad1.b == false && UpOrDown == false) {
                tailServo.setDirection(Servo.Direction.FORWARD);
                tailServo.setPosition(0.25);
                UpOrDown = true;
            }

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            /*
            if(gamepad1.dpad_down == true && moveUpOrDown == true) {
                forkliftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
                forkliftDrive.setPower(0.2);
                moveUpOrDown = false;
            } else if(gamepad1.dpad_down == false && moveUpOrDown == true) {
                forkliftDrive.setPower(0.0);
            }

            if(gamepad1.dpad_up == true && moveUpOrDown == false) {
                forkliftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                forkliftDrive.setPower(-0.2);
                moveUpOrDown = true;
            } else if(gamepad1.dpad_up == false && moveUpOrDown == false) {
                forkliftDrive.setPower(0.0);
            }
            */



            if(gamepad1.dpad_up)
            {
                forkliftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                forkliftDrive.setPower(-0.3);
                //wait(40);
                forkliftDrive.setPower(0.0);

                InsideLoopUp++;
            }

            if(gamepad1.dpad_down)
            {
                forkliftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                forkliftDrive.setPower(0.2);
                //wait(40);
                forkliftDrive.setPower(0.0);

                InsideLoopDown++;
            }



            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            speed = 0.5;
            leftPower  = speed * gamepad1.left_stick_y ;
            rightPower = speed * gamepad1.right_stick_y ;
            buttonPress = gamepad1.a;
            servoPos = clawServo1.getPosition();
            // Send calculated power to wheels

            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            leftPowerPrev = leftPower;

            // Show the elapsed game time and wheel power.
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("MoveUpCount", " " + InsideLoopUp);
            telemetry.addData("MoveDownCount", " " + InsideLoopDown);
            telemetry.addData("whichLoop", " " + whichLoop);
            //telemetry.addData("Button Press A:", " " + buttonPress);
            //telemetry.addData("MoveBackOrFront Status", " " + moveBackOrFront);
            //telemetry.addData("Servo Current Position:", " " + servoPos);
            //telemetry.addData("moveBackorFront(True/False):", " " + moveBackOrFront);
            telemetry.addData("Loop #:", " " + loop);
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}


