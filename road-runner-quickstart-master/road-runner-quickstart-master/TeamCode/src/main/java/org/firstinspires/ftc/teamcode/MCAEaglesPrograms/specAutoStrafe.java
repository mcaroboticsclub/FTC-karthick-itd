package org.firstinspires.ftc.teamcode.MCAEaglesPrograms;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "Specimen Auto Strafe", group = "MCA Eagles Programs")
public final class specAutoStrafe extends LinearOpMode {

    DcMotorEx viperVL = null;
    DcMotorEx viperVR = null;
    Servo clawL, clawR;
    Servo specClaw = null;

    public void close() {
        specClaw.setPosition(0.6);
    }

    public void open() {
        specClaw.setPosition(0.4);
    }

    public void moveVert(int pos) {

        viperVL.setTargetPosition(pos);
        viperVL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperVL.setPower(1);
        viperVR.setTargetPosition(pos);
        viperVR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperVR.setPower(1);
    }

    public void startRaise() {
        moveVert(-2000);
    }
    public void startDescent(){
        moveVert(0);
    }

    public void hangSpec() {
        moveVert(-1200);
        sleep(3000);
        open();
        sleep(1500);
        //moveVert(-800);
    }

    public void pickup() {
        moveVert(0);
        sleep(2000);
        close();
        sleep(500);
        startRaise();
        sleep(500);
    }

    public void runOpMode() throws InterruptedException {
        viperVL = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical_Left");
        viperVR = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical_Right");
        specClaw = hardwareMap.servo.get("Specimen_Claw");

        Servo rotWrist = hardwareMap.servo.get("Rotation_Wrist");
        Servo vipWrist = hardwareMap.servo.get("Viper_Wrist");

        Servo bucket = hardwareMap.servo.get("Bucket");
        //bucket.setPosition(0.7);

        viperVL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperVL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        viperVR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperVR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Pose2d beginPose = new Pose2d(9, -64, Math.toRadians(270));
        Pose2d specCenter = new Pose2d(0, -32.3, Math.toRadians(270));
        Pose2d spec1 = new Pose2d(-6, -32.3, Math.toRadians(270));
        Pose2d spec2 = new Pose2d(0, -32.3, Math.toRadians(270));
        Pose2d spec3 = new Pose2d(3, -32.3, Math.toRadians(270));
        Pose2d sampleStart = new Pose2d(36, -35.8, Math.toRadians(90));
        Pose2d sampleUp = new Pose2d(36, -10, Math.toRadians(90));
        Vector2d rotatePoint = new Vector2d(3, -48);

        Pose2d sam1 = new Pose2d(45, -20, Math.toRadians(90));
        Pose2d sam2 = new Pose2d(56, -20, Math.toRadians(90));
        Pose2d sam3 = new Pose2d(63, -20, Math.toRadians(90));

        Pose2d net1 = new Pose2d(45, -60, Math.toRadians(90));
        Pose2d net2 = new Pose2d(53, -60, Math.toRadians(90));
        Pose2d net3 = new Pose2d(61, -60, Math.toRadians(90));

        Pose2d waitArea = new Pose2d(58, -55, Math.toRadians(90));
        Pose2d park = new Pose2d(38, 0, Math.toRadians(270));
        Pose2d specCollect = new Pose2d(55, -67, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Action startToSpec = drive.actionBuilder(beginPose)
                .strafeTo(specCenter.position)
                .build();

        Action specCenterToSpec1 = drive.actionBuilder(specCenter)
                .strafeTo(spec1.position)
                .build();


        Action getAllSamples = drive.actionBuilder(spec1)
                .strafeToLinearHeading(rotatePoint, Math.toRadians(90))
                //.turnTo(Math.toRadians(90))
                .strafeTo(sampleStart.position)
                .strafeTo(sampleUp.position)
                .strafeTo(sam1.position)
                .strafeTo(net1.position)

                /*
                .strafeTo(sam1.position)
                .strafeTo(sam2.position)
                .strafeTo(net2.position)


                .strafeTo(sam2.position)
                .strafeTo(sam3.position)
                .strafeTo(net3.position)

                 */


                .strafeToLinearHeading(waitArea.position, waitArea.heading)
                .build();

        Action parkFromSample = drive.actionBuilder(waitArea)
                .splineTo(new Vector2d(46.50, -29.11), Math.toRadians(-87.59))
                .splineTo(new Vector2d(28.62, -46.50), Math.toRadians(186.01))
                .splineTo(new Vector2d(0.88, -45.43), Math.toRadians(177.78))
                .splineTo(new Vector2d(-23.06, -42.99), Math.toRadians(174.17))
                .splineTo(new Vector2d(-38.00, -27.35), Math.toRadians(133.72))
                .splineTo(new Vector2d(-34.88, -11.23), Math.toRadians(79.02))
                .build();
        Action goToSpecPickup = drive.actionBuilder(waitArea)
                .strafeTo(specCollect.position)
                .build();

        Action specCycle1 = drive.actionBuilder(specCollect)
                .strafeToLinearHeading(specCenter.position, specCenter.heading)
                .strafeTo(spec2.position)
                .build();

        Action goToSpecPickup2 = drive.actionBuilder(spec2)
                .strafeTo(rotatePoint)
                .strafeToLinearHeading(specCollect.position, specCollect.heading)
                .build();
        Action specCycle2 = drive.actionBuilder(specCollect)
                .strafeToLinearHeading(specCenter.position, specCenter.heading)
                .strafeTo(spec3.position)
                .build();

        //Action combined = new SequentialAction(startToSpec,specCenterToSpec1, getAllSamples, goToSpecPickup, specCycle1);
        close();
        waitForStart();
        startRaise();
        sleep(2000);
        Actions.runBlocking(startToSpec);
        Actions.runBlocking(specCenterToSpec1);
        hangSpec();
        Actions.runBlocking(getAllSamples);
        sleep(1000);

        Actions.runBlocking(goToSpecPickup);
        open();
        startDescent();
        sleep(500);
        close();
        sleep(2000);
        startRaise();
        sleep(1000);
        Actions.runBlocking(specCycle1);
        hangSpec();
        sleep(2000);

        Actions.runBlocking(goToSpecPickup2);
        startDescent();
        open();
        sleep(500);
        close();
        sleep(2000);
        startRaise();
        sleep(1000);
        Actions.runBlocking(specCycle2);
        hangSpec();
        sleep(2000);

        sleep(5000);

    }
}