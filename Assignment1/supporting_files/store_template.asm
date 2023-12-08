	.data
a:
	75
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	load %x0, $n, %x3
	load %x0, $a, %x4
	addi %x0, 0, %x5
	addi %x0, 10, %x10
	addi %x0, 0, %x6
loop:
	beq %x3, %x5, done
	load %x5, $a, %x6
	store %x6, 0, %x10
	addi %x10, 1, %x10
	addi %x5, 1, %x5
	jmp loop
done:
	end
