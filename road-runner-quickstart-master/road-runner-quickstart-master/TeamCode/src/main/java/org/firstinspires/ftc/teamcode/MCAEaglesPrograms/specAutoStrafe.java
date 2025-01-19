package org.firstinspires.ftc.teamcode.MCAEaglesPrograms;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
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

    public void startDescent() {
        moveVert(0); //0 for bottom
    }

    public void hangSpec() {
        moveVert(-1400);
        sleep(250); //3000
        open();
        sleep(250); //1500
        //moveVert(-800);
    }

    public void pickup() {
        moveVert(0);
        sleep(250); //2000
        close();
        sleep(250);
        startRaise();
        sleep(250);
    }

    public void runOpMode() throws InterruptedException {
        viperVL = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical_Left");
        viperVR = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical_Right");
        specClaw = hardwareMap.servo.get("Specimen_Claw");

        Servo bucket = hardwareMap.servo.get("Bucket");

        viperVL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperVL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        viperVR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperVR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Pose2d beginPose = new Pose2d(9, -64, Math.toRadians(270));
        Pose2d specCenter = new Pose2d(0, -32.3, Math.toRadians(270));

        Pose2d spec1 = new Pose2d(-4, -32.3, Math.toRadians(270));
        Pose2d spec2 = new Pose2d(2, -32.3, Math.toRadians(270));
        Pose2d spec3 = new Pose2d(6, -32.3, Math.toRadians(270));

        Pose2d sampleStart = new Pose2d(36, -35.8, Math.toRadians(90));
        Pose2d sampleUp = new Pose2d(40, -20, Math.toRadians(90)); //xpos: 36

        Vector2d rotatePoint = new Vector2d(3, -48);

        Pose2d sam1 = new Pose2d(45, -20, Math.toRadians(90));
        Pose2d sam2 = new Pose2d(55, -20, Math.toRadians(90));

        Pose2d net1 = new Pose2d(45, -60, Math.toRadians(90));
        Pose2d net2 = new Pose2d(55, -60, Math.toRadians(90));

        Pose2d waitArea = new Pose2d(58, -50, Math.toRadians(90));
        Pose2d specCollect = new Pose2d(45, -68.3, Math.toRadians(90));
        Pose2d specCollect3 = new Pose2d(46.75, -66.2, Math.toRadians(90));

        Pose2d waitArea3 = new Pose2d(45, -55, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Action startToSpec = drive.actionBuilder(beginPose)
                .strafeTo(specCenter.position)
                .build();

        Action specCenterToSpec1 = drive.actionBuilder(specCenter)
                .strafeTo(spec1.position)
                .build();

        Action getFirstSample = drive.actionBuilder(spec1)
                .strafeToLinearHeading(rotatePoint, Math.toRadians(90))
                .strafeTo(sampleStart.position)
                .strafeTo(sampleUp.position)
                .strafeTo(sam1.position)
                .strafeTo(net1.position)
                .strafeToLinearHeading(waitArea.position, waitArea.heading)
                .build();

        Action getSecondSample = drive.actionBuilder(spec2)
                .strafeToLinearHeading(rotatePoint, Math.toRadians(90))
                .strafeTo(sampleStart.position)
                .strafeTo(sampleUp.position)
                .strafeTo(sam2.position)
                .strafeTo(net2.position)
                .strafeToLinearHeading(waitArea.position, waitArea.heading)
                .build();

        Action goToSpecPickup = drive.actionBuilder(waitArea)
                .strafeToLinearHeading(specCollect.position, specCollect.heading)
                .build();

        Action specCycle2 = drive.actionBuilder(specCollect)
                .strafeToLinearHeading(specCenter.position, specCenter.heading)
                .strafeTo(spec2.position)
                .build();

        Action specCycle3 = drive.actionBuilder(specCollect)
                .strafeToLinearHeading(specCenter.position, specCenter.heading)
                .strafeToLinearHeading(spec3.position, spec3.heading)
                .build();

        Action goToWaitAfterSpec2 = drive.actionBuilder(spec2)
                .strafeToLinearHeading(waitArea.position, waitArea.heading)
                .build();

        Action goToSpecPickup3 = drive.actionBuilder(spec2)
                .strafeToLinearHeading(waitArea3.position, waitArea3.heading)
                .strafeToLinearHeading(specCollect3.position, specCollect3.heading.minus(Rotation2d.exp(Math.toRadians(5))))
                .build();

        close();
        bucket.setPosition(0.825);

        waitForStart();

        startRaise();
        sleep(200); //2000

        //go to hang preload
        Actions.runBlocking(startToSpec);
        Actions.runBlocking(specCenterToSpec1);
        hangSpec();

        //push 1st sample
        Actions.runBlocking(getFirstSample);
        sleep(200); //1000

        //get ready to pick specimen
        startDescent();
        open();

        // Go To Pickup.
        Actions.runBlocking(goToSpecPickup);
        sleep(200); //1000

        close();
        sleep(200); //1000

        startRaise();
        sleep(200); //1000

        Actions.runBlocking(specCycle2);
        //hang 2nd specimen
        hangSpec();


        // 3rd specimen
//        Actions.runBlocking(goToWaitAfterSpec2);
//        sleep(1000);

        // TODO: This crashes into the wall but works. Fix timings, use actions.
        Actions.runBlocking(goToSpecPickup3);

        startDescent();
        open();

        sleep(500); //500

        close();
        sleep(200); //2000

        startRaise();
        sleep(200); //1000

        Actions.runBlocking(specCycle3);
        //hang 3rd specimen
        hangSpec();


        //park (if time)
//         Actions.runBlocking(goToSpecPickup);
//         sleep(200);
    }
}