	.data
n:
	-1
	.text
main:
	load %x0, $n, %x4
	blt %x4, %x0, notpossible
	addi %x0, 1, %x5
	addi %x0, 0, %x6
	addi %x0, 1, %x7
	add %x0, %x0, %x8
	add %x0, %x0, %x10
	beq %x4, 1, basecase
	blt %x4, 1, notpossible
loop:
	beq %x5, %x4, done
	add %x6, %x7, %x8
	addi %x7, 0, %x6
	addi %x8, 0, %x7
	addi %x5, 1, %x5
	jmp loop
notpossible:
	subi %x0, 1, %x10
	end
basecase:
	add %x4, %x5, %x10
	end
done:
	add %x0, %x8, %x10
	end