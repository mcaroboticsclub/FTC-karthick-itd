// Import Required Files
package org.firstinspires.ftc.teamcode.MCAEaglesPrograms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// Send Code And Operating Mode To Game Board
@TeleOp(name = "Full Bot Drive", group = "MCA EAGLES PROGRAMS")
public class fullBotDrive extends LinearOpMode {

    // Define a max speed to control all bot functions.
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
    Servo specArm = null;
    Servo specClaw = null;
    Servo specRot = null;
    Servo sampleV = null;
    Servo sampleH = null;
    Servo sampleRot = null;
    Servo sampleClaw = null;

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
        specArm = hardwareMap.servo.get("Spec_Arm");
        specClaw = hardwareMap.servo.get("Spec_Claw");
        specRot = hardwareMap.servo.get("Spec_Rot");
        sampleV = hardwareMap.servo.get("Sample_V");
        sampleH = hardwareMap.servo.get("Sample_H");
        sampleRot = hardwareMap.servo.get("Sample_Rot");
        sampleClaw = hardwareMap.servo.get("Sample_Claw");

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

        // Make vipers run using a constant speed of 300 ticks per second.
        for (DcMotorEx viperV : vertical) {
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

            // If gamepad 2's left stick is pushed up or down, move the vertical viper forwards or backwards.
            if (gamepad2.left_stick_y < 0 && viperVL.getCurrentPosition() > -4300) {
                viperVL.setPower(gamepad2.left_stick_y * speedFactor);
                viperVLTargetPos = viperVL.getCurrentPosition();

                viperVR.setPower(viperVL.getPower());
                viperVRTargetPos = viperVR.getCurrentPosition();
            } else if (gamepad2.left_stick_y > 0) {
                viperVL.setPower(gamepad2.left_stick_y * speedFactor);
                viperVLTargetPos = viperVL.getCurrentPosition();

                viperVR.setPower(viperVL.getPower());
                viperVRTargetPos = viperVR.getCurrentPosition();
            } else {
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

            if (gamepad2.y && gamepad2.dpad_down) {
                sampleV.setPosition(1);
            }

            else if (gamepad2.y && gamepad2.dpad_up) {
                sampleV.setPosition(0.5);
            }

            else if (gamepad2.y && gamepad2.dpad_right) {
                sampleV.setPosition(1);
            }

            else if (gamepad2.y && gamepad2.left_stick_y != 0) {
                if (-gamepad2.left_stick_y > 0) {
                    sampleV.setPosition(Math.min(sampleV.getPosition() - gamepad2.left_stick_y * speedFactor, 1));
                }

                else if (-gamepad2.left_stick_y < 0) {
                    sampleV.setPosition(Math.max(sampleV.getPosition() + gamepad2.left_stick_y * speedFactor, 0));
                }
            }

            else if (gamepad2.y && gamepad2.dpad_left) {
                continue;
            }

            if (gamepad2.x && gamepad2.left_stick_x != 0) {
                if (gamepad2.left_stick_x > 0) {
                    sampleH.setPosition(Math.min(sampleH.getPosition() + gamepad2.left_stick_x * speedFactor, 1));
                }
                else if (gamepad2.left_stick_x < 0) {
                    sampleH.setPosition(Math.max(sampleH.getPosition() - gamepad2.left_stick_x * speedFactor, 0));
                }
            }

            if (gamepad2.x && gamepad2.left_bumper) {
                sampleClaw.setPosition(0.8);
            }

            else if (gamepad2.x && gamepad2.right_bumper) {
                sampleClaw.setPosition(0.3);
            }

            if (gamepad2.x && gamepad2.left_trigger != 0) {
                sampleRot.setPosition(Math.max(sampleRot.getPosition() - gamepad2.left_trigger * speedFactor, 0));
            }

            else if (gamepad2.x && gamepad2.right_trigger != 0) {
                sampleRot.setPosition(Math.min(sampleRot.getPosition() + gamepad2.right_trigger * speedFactor, 1));
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
            telemetry.addData("Speed Factor", speedFactor);

            // Update the telemetry.
            telemetry.update();
        }
    }
}