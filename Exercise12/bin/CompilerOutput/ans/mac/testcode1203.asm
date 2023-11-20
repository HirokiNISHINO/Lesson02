; 64 bit code.
bits 64
	; to use the printf() and strcmp functions.
extern _printf
extern _strcmp
extern _fmod

; data section.
section .data
	exit_fmt#:    db "exit code:%d", 10, 0 ; the format string for the exit message.

	print_int_fmt#:    db "%d", 0 ; the format string for the print int.
	print_string_fmt#:    db "%s", 0 ; the format string for the print string.
	print_double_fmt#:    db "%lf", 0 ; the format string for the print double.
	print_CR_fmt#:    db 10, 0 ; the format string for the print LF (\n).

	print_boolean_string_true#:    db "true", 0 ; the format string for the print double.
	print_boolean_string_false#:    db "false", 0 ; the format string for the print double.

	global_variable#a : times 8 db 0
	global_variable#b : times 8 db 0
	global_variable#bt : times 8 db 0
	global_variable#bf : times 8 db 0

; text section
section .text
	global _main ; the entry point.

; the subroutine for sys-exit. rax will be the exit code.
exit_program#:
	and rsp, 0xFFFFFFFFFFFFFFF0 ; stack must be 16 bytes aligned to call a C function.
	push rax ; we need to preserve rax here.
	push rax ; pushing twice for 16 byte alignment. We'll discard this later. 

	; call printf to print out the exit code.
	lea rdi, [rel exit_fmt#] ; the format string
	mov rsi, rax			; the exit code 
	mov rax, 0			; no xmm register is used.
	call _printf

	pop rax ; this value will be discared (as we did 'push rax' twice for 16 bytes alignment.

	mov rax, 0x2000001; specify the exit sys call.
	pop rdi ; this is the rax value we pushed at the entry of this sub routine
	syscall ; exit!

; the function for print(int).
print_int#:
	push rbp 		; store the current base pointer.
	mov  rbp, rsp 	; move the base pointer to the new stack frame.
	and  rsp, 0xFFFFFFFFFFFFFFF0	; to make stack 16 byte aligned (ABI requires this!).

	lea  rdi, [rel print_int_fmt#]
	mov  rsi, rax
	mov  rax, 0
	call _printf

	mov  rsp, rbp
	pop  rbp
	ret

; the function for print(string).
print_string#:
	push rbp 		; store the current base pointer.
	mov  rbp, rsp 	; move the base pointer to the new stack frame.
	and  rsp, 0xFFFFFFFFFFFFFFF0	; to make stack 16 byte aligned (ABI requires this!).

	lea  rdi, [rel print_string_fmt#]
	mov  rsi, rax
	mov  rax, 0
	call _printf

	mov  rsp, rbp
	pop  rbp
	ret

; the function for print(double).
print_double#:
	push rbp 		; store the current base pointer.
	mov  rbp, rsp 	; move the base pointer to the new stack frame.
	and  rsp, 0xFFFFFFFFFFFFFFF0	; to make stack 16 byte aligned (ABI requires this!).

	lea  rdi, [rel print_double_fmt#]
	movq xmm0, rax
	mov  rax, 1
	call _printf

	mov  rsp, rbp
	pop  rbp
	ret

; the function for print(boolean).
print_boolean#:
	push rbp 		; store the current base pointer.
	mov  rbp, rsp 	; move the base pointer to the new stack frame.
	and  rsp, 0xFFFFFFFFFFFFFFF0	; to make stack 16 byte aligned (ABI requires this!).

	cmp rax, 0
	je .print_boolean_false#

	.print_boolean_true#:
	lea rsi, [rel print_boolean_string_true#]
	jmp .print_boolean_print#

	.print_boolean_false#:
	lea rsi, [rel print_boolean_string_false#]

	.print_boolean_print#:
	lea rdi, [rel print_string_fmt#]
	mov rax, 0
	call _printf

	mov  rsp, rbp
	pop  rbp
	ret

; the function for printCR.
print_CR#:
	push rbp 		; store the current base pointer.
	mov  rbp, rsp 	; move the base pointer to the new stack frame.
	and  rsp, 0xFFFFFFFFFFFFFFF0	; to make stack 16 byte aligned (ABI requires this!).

	lea  rdi, [rel print_CR_fmt#]
	mov  rax, 0
	call _printf

	mov  rsp, rbp
	pop  rbp
	ret

_main:
	mov rax, 0 ; initialize the accumulator register.
	mov rax, 10
	mov [ rel global_variable#a], rax
	mov rax, 99
	mov [ rel global_variable#b], rax
	mov rax, 1
	mov [ rel global_variable#bt], rax
	mov rax, 0
	mov [ rel global_variable#bf], rax
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#a]
	push rax
	mov rax, 10
	mov rbx, rax
	pop rax
	cmp rax, rbx
	je cond_13_true
cond_13_false:
	mov rax, 0
	jmp cond_13_end
cond_13_true:
	mov rax, 1
cond_13_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#a]
	push rax
	mov rax, 99
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jne cond_14_true
cond_14_false:
	mov rax, 0
	jmp cond_14_end
cond_14_true:
	mov rax, 1
cond_14_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	call print_CR#
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#a]
	push rax
	mov rax, [ rel global_variable#b]
	mov rbx, rax
	pop rax
	cmp rax, rbx
	je cond_15_true
cond_15_false:
	mov rax, 0
	jmp cond_15_end
cond_15_true:
	mov rax, 1
cond_15_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#a]
	push rax
	mov rax, [ rel global_variable#b]
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jne cond_16_true
cond_16_false:
	mov rax, 0
	jmp cond_16_end
cond_16_true:
	mov rax, 1
cond_16_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	call print_CR#
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#bt]
	push rax
	mov rax, 1
	mov rbx, rax
	pop rax
	cmp rax, rbx
	je cond_17_true
cond_17_false:
	mov rax, 0
	jmp cond_17_end
cond_17_true:
	mov rax, 1
cond_17_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#bt]
	push rax
	mov rax, 1
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jne cond_18_true
cond_18_false:
	mov rax, 0
	jmp cond_18_end
cond_18_true:
	mov rax, 1
cond_18_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	call print_CR#
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#bt]
	push rax
	mov rax, [ rel global_variable#bf]
	mov rbx, rax
	pop rax
	cmp rax, rbx
	je cond_19_true
cond_19_false:
	mov rax, 0
	jmp cond_19_end
cond_19_true:
	mov rax, 1
cond_19_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#bt]
	push rax
	mov rax, [ rel global_variable#bf]
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jne cond_20_true
cond_20_false:
	mov rax, 0
	jmp cond_20_end
cond_20_true:
	mov rax, 1
cond_20_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	call print_CR#
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#bt]
	push rax
	mov rax, 5
	push rax
	mov rax, 10
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jl cond_22_true
cond_22_false:
	mov rax, 0
	jmp cond_22_end
cond_22_true:
	mov rax, 1
cond_22_end:
	mov rbx, rax
	pop rax
	cmp rax, rbx
	je cond_21_true
cond_21_false:
	mov rax, 0
	jmp cond_21_end
cond_21_true:
	mov rax, 1
cond_21_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	push rax
	mov rax, [ rel global_variable#bt]
	push rax
	mov rax, 5
	push rax
	mov rax, 10
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jl cond_24_true
cond_24_false:
	mov rax, 0
	jmp cond_24_end
cond_24_true:
	mov rax, 1
cond_24_end:
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jne cond_23_true
cond_23_false:
	mov rax, 0
	jmp cond_23_end
cond_23_true:
	mov rax, 1
cond_23_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	call print_CR#
	mov rax, 0
	push rax
	mov rax, 8
	push rax
	mov rax, 8
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jle cond_26_true
cond_26_false:
	mov rax, 0
	jmp cond_26_end
cond_26_true:
	mov rax, 1
cond_26_end:
	push rax
	mov rax, [ rel global_variable#bf]
	mov rbx, rax
	pop rax
	cmp rax, rbx
	je cond_25_true
cond_25_false:
	mov rax, 0
	jmp cond_25_end
cond_25_true:
	mov rax, 1
cond_25_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	push rax
	mov rax, 8
	push rax
	mov rax, 8
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jle cond_28_true
cond_28_false:
	mov rax, 0
	jmp cond_28_end
cond_28_true:
	mov rax, 1
cond_28_end:
	push rax
	mov rax, [ rel global_variable#bf]
	mov rbx, rax
	pop rax
	cmp rax, rbx
	jne cond_27_true
cond_27_false:
	mov rax, 0
	jmp cond_27_end
cond_27_true:
	mov rax, 1
cond_27_end:
	call print_boolean#
	pop rbx
	add rax, rbx
	call print_CR#
	mov rax, 0
	call print_CR#

	jmp exit_program# ; exit the program, rax should hold the exit code.
