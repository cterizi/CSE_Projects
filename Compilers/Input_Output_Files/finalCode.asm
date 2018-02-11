j 90
130:140:lw $t1,-12($sp)
lw $t0,-8($sp)
sw $t1,($t0)
150:lw $ra,($sp)
jr $ra
120:160:li $t1,10
lw $t0,-8($sp)
sw $t1,($t0)
170:lw $ra,($sp)
jr $ra
110:180:190:200:add $fp,$sp,20
li $t0,50
sw $t0,-(12+4)($fp)
add $t0,$sp,-20
sw $t0,-8($fp)
lw $t0,-4($sp)
sw $t0,-4($fp)
add $sp,$sp,20
jal f2
add $sp,$sp,-20
sw $ra,($sp)
210:li $t1,12
lw $t2,-20($sp)
add$t1,$t1,$t2
sw $t1,-24($sp)
220:lw $t1,-24($sp)
sw $t1,-16($sp)
230:lw $t1,-16($sp)
lw $t0,-8($sp)
sw $t1,($t0)
240:lw $ra,($sp)
jr $ra
260:270:li $t1,10
li $t2,4
sub$t1,$t1,$t2
sw $t1,-20($sp)
280:li $t1,45
li $t2,32
div$t1,$t1,$t2
sw $t1,-24($sp)
290:lw $t1,-20($sp)
lw $t2,-24($sp)
mul$t1,$t1,$t2
sw $t1,-28($sp)
300:li $t1,21
li $t2,43
mul$t1,$t1,$t2
sw $t1,-32($sp)
310:lw $t1,-28($sp)
lw $t2,-32($sp)
add$t1,$t1,$t2
sw $t1,-36($sp)
320:lw $t1,-36($sp)
sw $t1,-16($sp)
330:lw $ra,($sp)
jr $ra
250:340:350:360:add $fp,$sp,16
lw $t0,-12($sp)
sw $t0,-(12+4)($fp)
add $t0,$sp,-16
sw $t0,-8($fp)
lw $t0,-4($sp)
sw $t0,-4($fp)
add $sp,$sp,16
jal p2
add $sp,$sp,-16
sw $ra,($sp)
370:lw $t1,-16($sp)
sw $t1,-12($sp)
380:lw $ra,($sp)
jr $ra
90: add $sp,$sp,40
move $s0,$sp
100:390:add $fp,$sp,24
lw $t0,-4($sp)
sw $t0,-4($fp)
add $sp,$sp,24
jal f1
add $sp,$sp,-24
sw $ra,($sp)
400:lw $t1,-12($s0)
lw $t2,-16($s0)
add$t1,$t1,$t2
sw $t1,-24($s0)
410:lw $t1,-16($s0)
lw $t2,-20($s0)
add$t1,$t1,$t2
sw $t1,-28($s0)
420:lw $t1,-24($s0)
lw $t2,-28($s0)
bne,$t1,$t2,440
430:j 490
440:j 480
450:lw $t1,-12($s0)
li $t2,2
mul$t1,$t1,$t2
sw $t1,-32($s0)
460:lw $t1,-32($s0)
li $t2,0
bge,$t1,$t2,440
470:j 480
480:j 490
490:li $t1,1
li $t2,1
bne,$t1,$t2,510
500:j 540
510:lw $t1,-12($s0)
li $t2,10
add$t1,$t1,$t2
sw $t1,-36($s0)
520:li $v0,1
li $a0,T_11
syscall
530:j 490
540:li $t1,0
sw $t1,-40($s0)
550:lw $t1,-12($s0)
li $t2,1
bne,$t1,$t2,590
560:li $t1,1
sw $t1,-40($s0)
570:li $v0,1
li $a0,1
syscall
580:li $t1,1
lw $t2,-40($s0)
beq,$t1,$t2,680
590:lw $t1,-12($s0)
li $t2,2
bne,$t1,$t2,630
600:li $t1,1
sw $t1,-40($s0)
610:li $v0,1
li $a0,2
syscall
620:li $t1,1
lw $t2,-40($s0)
beq,$t1,$t2,680
630:lw $t1,-12($s0)
li $t2,3
bne,$t1,$t2,670
640:li $t1,1
sw $t1,-40($s0)
650:li $v0,1
li $a0,3
syscall
660:li $t1,1
lw $t2,-40($s0)
beq,$t1,$t2,680
670:li $v0,1
li $a0,0
syscall
680:690: