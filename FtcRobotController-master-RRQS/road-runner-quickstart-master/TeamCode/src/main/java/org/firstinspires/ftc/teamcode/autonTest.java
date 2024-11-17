package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * Test the dashboard gamepad integration.
 */
@Autonomous(name = "Test Auton Spec + Bask dont run", group = "MCA Eagles Group")
public class autonTest extends LinearOpMode {
    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotorEx viperV = null;
    DcMotorEx viperH = null;
    DcMotor susL = null;
    DcMotor susR = null;
    Servo sClawL = null;
    Servo sClawR = null;
    CRServo intake = null;
    Servo wrist = null;
    Servo bucket = null;
    Servo susGrip = null;

    public void openClaw() {
        sClawL.setPosition(0.4875);
        sClawR.setPosition(0.4625);
    }

    public void closeClaw() {
        sClawL.setPosition(0.3);
        sClawR.setPosition(0.7);
    }

    public void activateIntakeIn() {
        intake.setPower(-1);
    }

    public void activateIntakeOut() {
        intake.setPower(1);
    }

    public void deactivateIntake() {
        intake.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        viperV = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical");
        viperH = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Horizontal");
        susL = hardwareMap.dcMotor.get("Suspension_Left");
        susR = hardwareMap.dcMotor.get("Suspension_Right");

        // Hardware Map Servos
        sClawL = hardwareMap.servo.get("Claw_Left");
        sClawR = hardwareMap.servo.get("Claw_Right");
        intake = hardwareMap.crservo.get("Intake");
        wrist = hardwareMap.servo.get("Intake_Wrist");
        bucket = hardwareMap.servo.get("Bucket");
        susGrip = hardwareMap.servo.get("Suspension_Grip");

        // Set All Motors To Brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperV.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperH.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        susL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        susR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse Direction Of One Side's Wheel Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        public void runOpMode() {

        }
    }
}

