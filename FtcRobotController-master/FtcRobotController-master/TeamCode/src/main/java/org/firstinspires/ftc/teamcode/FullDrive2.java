// Import Required Files
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
//import org.firstinspires.ftc.teamcode.util.Encoder;

@Disabled

// Send Code And Operating Mode To Game Board
@TeleOp(name = "Srijith FullDrive2", group = "MCA EAGLES PROGRAMS")
public class FullDrive2 extends LinearOpMode {

    double speedFactor = 0.8;

    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor verticalViper = null;
    DcMotor horViper = null;
    DcMotor rightActuator = null;
    DcMotor leftActuator = null;

    Servo bucket = null;
    CRServo intakeServo = null;
    Servo clawL = null;
    Servo clawR = null;
    Servo intakeWrist = null;

    int vertViperUpPos = -11500;
    int vertViperDownPos = 0;

    int horViperExtendPos = -1900;
    int horViperRetractPos = 0;

    double intakeWristUp = -0.6;
    double intakeWristLevel = 0.3;
    double intakeWristPickup = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {


        // Hardware Map All Motors
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        verticalViper = hardwareMap.dcMotor.get("Viper_Vertical");
        horViper = hardwareMap.dcMotor.get("Viper_Horizontal");
        intakeServo = hardwareMap.crservo.get("Intake");
        clawL = hardwareMap.servo.get("Claw_Left");
        clawR = hardwareMap.servo.get("Claw_Right");
        intakeWrist = hardwareMap.servo.get("Intake_Wrist");
        bucket = hardwareMap.servo.get("Bucket");


        // Set The Motors To Brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalViper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalViper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horViper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        horViper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Reverse Direction Of One Side's Wheel Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();

        double initialPos = verticalViper.getCurrentPosition();
        boolean intakeOn=false;
        //0 means reverse, 1 means off, 2 means on

        while (opModeIsActive()) {
            //telemetry.addData("Left pod position: ", leftEncoder.getCurrentPosition());
            //telemetry.addData("Right pod position: ", rightEncoder.getCurrentPosition());
            //telemetry.addData("Back pod position: ", frontEncoder.getCurrentPosition());
            telemetry.update();
            // Set drivetrain to move based off of the inputs of the left and right sticks of gamepad 1.
            //drivetrain
            frontLeft.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*speedFactor);
            backLeft.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*speedFactor);
            frontRight.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*speedFactor);
            backRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*speedFactor);

            //secondary controls


            //vertical viper
            double vertViperPow = (gamepad2.right_stick_y)*speedFactor;
            if (vertViperPow > 0){
                verticalViper.setTargetPosition(vertViperUpPos);
                verticalViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                verticalViper.setTargetPosition(vertViperDownPos);
                verticalViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            verticalViper.setPower(Math.abs(vertViperPow));


            //horizontal viper
            double horViperPow = (gamepad2.left_stick_y)*speedFactor;

            if (horViperPow > 0){
                horViper.setTargetPosition(horViperExtendPos);
                horViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                horViper.setTargetPosition(horViperRetractPos);
                horViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }


            horViper.setPower((horViperPow));

            telemetry.addData("Initial viper Position", initialPos);
            telemetry.addData("Current viper position", horViper.getCurrentPosition());
            telemetry.addData("Y position", horViperPow);
            //rightActuator.setPower(gamepad2.left_stick_y*speedFactor);

            //intake

            if (gamepad2.left_trigger>0) intakeServo.setPower(1);
            else if (gamepad2.right_trigger>0) intakeServo.setPower(-1);
            else intakeServo.setPower(0);


            if (gamepad2.dpad_down) {
                intakeWrist.setPosition(0.88);
            }
            else if (gamepad2.dpad_left) {
                intakeWrist.setPosition(0.26);
            }
            else if (gamepad2.dpad_up) {
                intakeWrist.setPosition(0.5);
            }


            if (gamepad2.right_bumper) {
                bucket.setPosition(0.8975);
            }
            if (gamepad2.left_bumper) {
                bucket.setPosition(0.1);
            }
            if (gamepad2.x){
                intakeServo.setPower(-0.5);
                sleep(1500);
                horViper.setTargetPosition(horViperRetractPos);
                horViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                horViper.setPower(0.5);
                intakeWrist.setPosition(0.26);
                bucket.setPosition(0.875);
                sleep(2000);
                intakeServo.setPower(1);
                sleep(2500);
                intakeServo.setPower(0);
                intakeWrist.setPosition(0.5);
            }


            //specimen claw
            if (gamepad2.a) clawL.setPosition(0.0895); //close
            if (gamepad2.b) clawL.setPosition(0.25); //open

            if (gamepad2.a) clawR.setPosition(0.865); //close
            if (gamepad2.b) clawR.setPosition(0.65); //open


            //-11641


        }
    }
}