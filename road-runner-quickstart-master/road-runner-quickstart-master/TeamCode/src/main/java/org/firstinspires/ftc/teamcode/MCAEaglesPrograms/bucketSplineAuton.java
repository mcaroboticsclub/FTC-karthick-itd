package org.firstinspires.ftc.teamcode.MCAEaglesPrograms;

import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.lang.Math;

/**
 * Test the dashboard gamepad integration.
 */
@Autonomous(name = "Bucket Spline Auton", group = "MCA Eagles Programs")
public class bucketSplineAuton extends LinearOpMode {

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

    boolean specClawOpen = false;
    boolean sampClawOpen = false;

    public Action modVertVipers(int pos) {
        viperVL.setTargetPosition(pos);
        viperVR.setTargetPosition(pos);

        viperVL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperVR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        return new SequentialAction();
    }

    public Action modHorizVipers(int pos) {
        viperH.setTargetPosition(pos);
        viperH.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        return new SequentialAction();
    }

    public Action toggleSpecClaw() {
        if (specClawOpen) {
            specClawOpen = false;
            specClaw.setPosition(0.6);
        } else {
            specClawOpen = true;
            specClaw.setPosition(0.4);
        }

        return new SequentialAction();
    }

    public Action toggleSampClaw() {
        if (sampClawOpen) {
            sampClawOpen = false;
            sampClaw.setPosition(0.765);
        } else {
            sampClawOpen = true;
            sampClaw.setPosition(0.5);
        }
        return new SequentialAction();
    }

    public Action wristPos(String pos) {
        switch (pos) {
            case "U":
                vipWrist.setPosition(0.9);
                rotWrist.setPosition(0.2);
                break;
            case "D":
                vipWrist.setPosition(0.57);
                rotWrist.setPosition(0.54);
                break;
            case "c L":
                vipWrist.setPosition(0.98);
                rotWrist.setPosition(0.85);
                break;
            case "R":
                vipWrist.setPosition(0.98);
                rotWrist.setPosition(0.85); // Only down to left.


                sleep(600);
                sampClaw.setPosition(0.5);

                sleep(500);

                vipWrist.setPosition(0.9);
                rotWrist.setPosition(0.2);
                break;
        }

        return new SequentialAction();
    }

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors

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

        // Configure vipers to run to a targeted position.
        viperVL.setTargetPosition(0);
        viperVR.setTargetPosition(0);

        // Create the mecanum drivetrain in roadrunner with the defined hardware map and the initial pose of the robot.
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-16.15, -60.00, Math.toRadians(270.00)));

        // Go to the specimen scoring zone.
        Action specimenScore = drive.actionBuilder(new Pose2d(-8.77, -60.69, Math.toRadians(270.00)))
                .splineToLinearHeading(new Pose2d(-6.46, -33.00, Math.toRadians(270.00)), Math.toRadians(76.76))
                .build();
//                .splineToLinearHeading(new Pose2d(-58.62, -39.92, Math.toRadians(90.00)), Math.toRadians(92.44))
//                .splineToLinearHeading(new Pose2d(-26.77, 6.92, Math.toRadians(180.00)), Math.toRadians(51.15))
//                .build();

        Action colSamp1 = drive.actionBuilder(new Pose2d(-6.46, -33.00, Math.toRadians(270.00)))
            .splineToLinearHeading(new Pose2d(-48.50, -40.12, Math.toRadians(90.00)), Math.toRadians(214.09))
            .build();

        Action bucketScore1 = drive.actionBuilder(new Pose2d(-48.00, -40.62, Math.toRadians(90.00)))
            .splineToLinearHeading(new Pose2d(-59.08, -58.85, Math.toRadians(45.00)), Math.toRadians(235.73))
            .build();

        Action colSamp2 = drive.actionBuilder(new Pose2d(-59.08, -58.85, Math.toRadians(45.00)))
            .splineToLinearHeading(new Pose2d(-58.62, -39.92, Math.toRadians(90.00)), Math.toRadians(92.44))
            .build();

        specClaw.setPosition(0.6);
        specClawOpen = false;

        sampClaw.setPosition(0.5);
        sampClawOpen = true;

        // Wait for the start button to be pressed.
        waitForStart();

        // If the stop button is pressed, stop the run function.
        if (isStopRequested()) return;

        // Once the start button is pressed, run the functions defined previously.
        Actions.runBlocking(
                new SequentialAction(
//                        new ParallelAction(
//                                specimenScore, new SequentialAction(modVertVipers(-2000))
//                        ),
//                        new SleepAction(1),

//                        new SequentialAction(modVertVipers(-1400)), new SequentialAction(toggleSpecClaw()),
//                        new SleepAction(0.2),

                        new ParallelAction(
                                new SequentialAction(modVertVipers(0)), colSamp1
                        ),
                        new SleepAction(1),

                        new ParallelAction(
                                new SequentialAction(modHorizVipers(-300)), new SequentialAction(wristPos("D")), new SequentialAction(toggleSampClaw()), bucketScore1
                        ),
                        new SleepAction(0.5),

                        new SequentialAction(toggleSampClaw())
                )
        );

    }
}
