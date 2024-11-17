package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.List;

/*
 * Test the dashboard gamepad integration.
 */
@Autonomous
public class initialAuton extends LinearOpMode {
    DcMotor frontLeft, frontRight, backLeft, backRight;
    DcMotor verticalViper = null;
    Servo clawL, clawR;
    double wheelDiameter=96/25.4; //wheelDiameter in inches
    double convertToRot(double inches){
        return (inches/(wheelDiameter*(Math.PI)))*537.6;
    }
    //convert inches to rotations
    public void move(double inches, int a, int b, int c, int d){
        move(inches, a, b, c, d, 0.5);
    }
    public void move(double inches, int a, int b, int c, int d, double power) {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int dist = (int) convertToRot(inches);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + dist * a);
        backRight.setTargetPosition(backRight.getCurrentPosition() + dist * b);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + dist * c);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + dist * d);
        // set motors to run to target encoder position and stop with brakes on.
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        frontRight.setPower(power);
        // wait while opmode is active and left motor is busy running to position.


        while (opModeIsActive() && backLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", backLeft.getCurrentPosition() + "  busy=" + backLeft.isBusy());
            telemetry.addData("encoder-fwd-right", backRight.getCurrentPosition() + "  busy=" + backRight.isBusy());
            telemetry.update();
            idle();
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }
    public void moveWithEncoder(double e, int a, int b, int c, int d) {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int dist = (int)e;
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + dist * a);
        backRight.setTargetPosition(backRight.getCurrentPosition() + dist * b);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + dist * c);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + dist * d);
        // set motors to run to target encoder position and stop with brakes on.
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setPower(0.2);
        backRight.setPower(0.2);
        frontLeft.setPower(0.2);
        frontRight.setPower(0.2);
        // wait while opmode is active and left motor is busy running to position.


        while (opModeIsActive() && backLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", backLeft.getCurrentPosition() + "  busy=" + backLeft.isBusy());
            telemetry.addData("encoder-fwd-right", backRight.getCurrentPosition() + "  busy=" + backRight.isBusy());
            telemetry.update();
            idle();
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void forward(double dist){

        move(dist, 1,1,1,1);
    }
    public void backward(double dist){
        move(dist, -1, -1, -1, -1);
    }
    public void right(double dist){
        dist*=((double)1/0.9);
        move(dist, -1,1,1,-1);
    }
    public void left(double dist){
        dist*=((double)1/0.9);
        move(dist, 1,-1,-1,1);
    } //7,
    public void rotate(double angle){
        double val=((double)4290)*(angle/(double)360);
        moveWithEncoder(val,1,-1,1,-1);
    }
    int vertViperUpPos = -11000;
    int vertViperDownPos = 0;
    int specHangPos = -3800;
    public void moveVert(int pos){
        verticalViper.setTargetPosition(pos);
        verticalViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalViper.setPower(0.75);
    }
    public void moveVertToWall(){
        moveVert(0);
    }
    public void moveVertDown(){
        moveVert(vertViperDownPos);
    }
    public void hangSpecimen(){
        moveVert(-6000);
        sleep(3000);
        backward(2);
        sleep(3000);
        moveVert(specHangPos);
        sleep(5000);
        open();
        sleep(1000);
        moveVert(vertViperDownPos);
        sleep(1000);
    }

    public void close(){
        clawL.setPosition(0.0895);
        clawR.setPosition(0.865);
    }
    public void open(){
        clawL.setPosition(0.25);
        clawR.setPosition(0.65);
    }


    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        clawL = hardwareMap.servo.get("Claw_Left");
        clawR = hardwareMap.servo.get("Claw_Right");
        verticalViper = hardwareMap.dcMotor.get("Viper_Vertical");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        verticalViper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalViper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        close();
        waitForStart();
        //add commands here
        backward(25.2);
        sleep(5000);
        hangSpecimen();
        sleep(2500);
        //backward(25.2);
        sleep(5000);


    }   // end runOpMode()
}
