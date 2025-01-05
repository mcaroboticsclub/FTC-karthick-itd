// Import Required Files
package org.firstinspires.ftc.teamcode.MCAEaglesPrograms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

// Send Code And Operating Mode To Game Board
@TeleOp(name = "Full Bot Drive", group = "MCA EAGLES PROGRAMS")
public class fullBotDrive extends LinearOpMode {

    double speedFactor = 1.0;

    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotorEx viperVL = null;
    DcMotorEx viperVR = null;
    DcMotorEx viperH = null;

    // Define All Servos
    Servo specClaw = null;
    Servo sampClaw = null;
    Servo vipWrist = null;
    Servo rotWrist = null;
    Servo bucket = null;


    // Define opmode.
    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        viperVL = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical_Left");
        viperVR = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical_Right");
        viperH = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Horizontal");

        // Hardware Map Servos
        specClaw = hardwareMap.servo.get("Specimen_Claw");
        sampClaw = hardwareMap.servo.get("Sample_Claw");
        vipWrist = hardwareMap.servo.get("Viper_Wrist");
        rotWrist = hardwareMap.servo.get("Rotation_Wrist");
        bucket = hardwareMap.servo.get("Bucket");

        // Set All Motors To Brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperVL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperVR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperH.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse Direction Of One Side's Wheel Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Make an array of vertical vipers to store them.
        DcMotorEx[] vertical = {viperVL, viperVR};

        // Make vipers run using constant speed of 300 ticks per second.
        for(DcMotorEx viperV : vertical) {
            viperV.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            viperV.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            viperV.setVelocity(300);
        }

        // Set horizontal vipers to run using a constant speed of 200 ticks per second.
        viperH.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperH.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperH.setVelocity(200);
        int viperVLTargetPos = viperVL.getCurrentPosition();
        int viperVRTargetPos = viperVL.getCurrentPosition();

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();

        while (opModeIsActive()) {

            // Set drivetrain to move based off of the inputs of the left and right sticks of gamepad 1.
            frontLeft.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * speedFactor);
            backLeft.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * speedFactor);
            frontRight.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * speedFactor);
            backRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * speedFactor);

            // If gamepad 2's right stick is pushed up or down, move the horizontal viper forwards or backwards.
            if (gamepad2.left_stick_y < 0 && viperVL.getCurrentPosition() > -4300) {
                viperVL.setPower(gamepad2.left_stick_y * speedFactor);
                viperVLTargetPos = viperVL.getCurrentPosition();

                viperVR.setPower(viperVL.getPower());
                viperVRTargetPos = viperVR.getCurrentPosition();
            }

            else if (gamepad2.left_stick_y > 0) {
                viperVL.setPower(gamepad2.left_stick_y * speedFactor);
                viperVLTargetPos = viperVL.getCurrentPosition();

                viperVR.setPower(viperVL.getPower());
                viperVRTargetPos = viperVR.getCurrentPosition();
            }

            else {
                viperVL.setPower(0);
                viperVR.setPower(0);
//                viperVL.setTargetPosition(viperVLTargetPos);
//                viperVL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//                viperVR.setTargetPosition(viperVRTargetPos);
//                viperVR.setTargetPosition(viperVRTargetPos);
//            }
            }

            // If gamepad 2's right stick is pushed up or down, move the horizontal viper forwards or backwards.
            if (gamepad2.right_stick_y < 0 && viperH.getCurrentPosition() > -1400) {
                viperH.setPower(gamepad2.right_stick_y * speedFactor);
            } else if (gamepad2.right_stick_y > 0) {
                viperH.setPower(gamepad2.right_stick_y * speedFactor);
            } else {
                viperH.setPower(0);
            }

            if (gamepad2.a) {
                specClaw.setPosition(0.6);
            }

            else if (gamepad2.b) {
                specClaw.setPosition(0.4);
            }

            if (gamepad2.x) {
                sampClaw.setPosition(0.765);
            }

            else if (gamepad2.y) {
                sampClaw.setPosition(0.5);
            }

            if (gamepad2.dpad_down) {
                vipWrist.setPosition(0.57);
                rotWrist.setPosition(0.54);
            }

            else if (gamepad2.dpad_left) {
                vipWrist.setPosition(0.98);
                rotWrist.setPosition(0.85); // Only down to left.
            }
            else if (gamepad2.dpad_up) {
                vipWrist.setPosition(0.9);
                rotWrist.setPosition(0.2);
            }

            else if (gamepad2.dpad_right) {
                vipWrist.setPosition(0.98);
                rotWrist.setPosition(0.85); // Only down to left.

                sleep(600);
                sampClaw.setPosition(0.5);

                sleep(500);

                vipWrist.setPosition(0.9);
                rotWrist.setPosition(0.2);
            }

            if (gamepad2.right_bumper) {
                bucket.setPosition(0.08);
            }

            else if (gamepad2.left_bumper) {
                bucket.setPosition(0.825);
            }

            // If gamepad 1's a button is pushed, set the max speed of all parts on the robot to full power.
            if (gamepad1.a) {
                speedFactor = 1.0;
            }

            // Otherwise, if gamepad 1's b button is pushed, set the max speed to 50% power.
            else if (gamepad1.b) {
                speedFactor = 0.5;
            }

            // Add telemetry data for all parts of the robot, for all motors and servos, for power and position, and for the current speed factor.
            telemetry.addData("Front Left Motor Power", frontLeft.getPower());
            telemetry.addData("Front Left Motor Position", frontLeft.getCurrentPosition());
            telemetry.addData("Front Right Motor Power", frontRight.getPower());
            telemetry.addData("Front Right Motor Position", frontRight.getCurrentPosition());
            telemetry.addData("Back Left Motor Power", backLeft.getPower());
            telemetry.addData("Back Left Motor Position", backLeft.getCurrentPosition());
            telemetry.addData("Back Right Motor Power", backRight.getPower());
            telemetry.addData("Back Right Motor Position", backRight.getCurrentPosition());
            telemetry.addData("Viper Left Vertical Motor Power", viperVL.getPower());
            telemetry.addData("Viper Left Vertical Motor Position", viperVL.getCurrentPosition());
            telemetry.addData("Viper Right Vertical Motor Power", viperVR.getPower());
            telemetry.addData("Viper Right Vertical Motor Position", viperVR.getCurrentPosition());
            telemetry.addData("Viper Horizontal Motor Power", viperH.getPower());
            telemetry.addData("Viper Horizontal Motor Position", viperH.getCurrentPosition());
            telemetry.addData("Specimen Claw Servo Position", specClaw.getPosition());
            telemetry.addData("Sample Claw Servo Position", sampClaw.getPosition());
            telemetry.addData("Viper Wrist Servo Position", vipWrist.getPosition());
            telemetry.addData("Rotation Wrist Servo Position", rotWrist.getPosition());
            telemetry.addData("Bucket Servo Position", bucket.getPosition());
            telemetry.addData("Speed Factor", speedFactor);

            // Update the telemetry.
            telemetry.update();
        }
    }
}