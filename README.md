# Raid-Zero Swerve code

quick intro to holonomic algorithms

(this was copy pasted from an addressed email, so the formatting is a little weird lol)
our swerve algorithm is actually fairly simple.
the first thing we do is create a module class, and we make a new module object for each swerve module on the bot.
let's define a few terms first. Each of the four wheel systems are called modules, which can independently rotate and move at different speeds.
there are two motors on each module. one motor, the rotor, controls the direction of the module
the other motor, the throttle, controls the actual wheel movement of the module.
the module class has a moveVector method, where we input a 2D vector and the module will 1. rotate to the desired direction and 2. move the wheel at the desired speed
the rotor needs to be set to a certain direction with closed loop control, while the throttle can be controlled in open loop control
now that we have the ability to move each module to match a certain module vector we can then coordinate the modules to move the robot in a certain way.
the modules are limited to 2 degrees of motion, so they can be described with a 2d vector. However, the robot moves with 3 degrees of motion (x,y,rotation), so they cant be directly converted. This extra degree of motion makes it more difficult to engineer than a normal tank drive, but it gives it more versatility. 
Fortunately, neither the equations nor the implementation of this is specifically difficult to do, but it takes some abstract thinking to understand it.
your parameters to this algorithm will be your 1. desired translational velocity
2. desired angular velocity
if u need autonomous functionality, you can use closed loop PID on the velocities for positional control
now for each module, you describe it using 2 basis vectors, NOT x-y basis vectors but translation-rotation basis vectors. lets call these bases v and w
v for each is equal to your desired V for your robot. this makes sense, because the physical translation of your modules should be exactly the same as your robot if you dont rotate. if you rotate, the average v of all 4 modules will still equal the desired translational velocity of the robot
(EQ 1)
now, to get the robot to spin at the rotational velocity w, you need to set the modules to spin in different directions. Imagine setting a robot to perform a point turn. To do this, you need to get the modules to point like so (FIG 1)
the direction of the modules are always the same for point turns but the speeds are not, so the right equation for turning is like so
(EQ 2)
this equation is a little more complicated, but all you do is get the cross product of the desired angular velocity vector of the robot with the radius vector of each module
(FIG 2 & 3)
finally, to get the output of that specific module, you add the two bases together
(EQ 3)

!![swervediagram](https://user-images.githubusercontent.com/36722504/112593651-c0e2b200-8e42-11eb-8750-e6d8d210b7f5.png)
[swervediagram](./swervediagram.png)
