all : foo.o goo.o moo.o loo.o
    gcc -g -c foo.o goo.o moo.o loo.o -o final

foo.o: foo.c foo.h
        gcc -g -c foo.o foo.c

goo.o: goo.c goo.h
        gcc -g -c goo.o goo.c

moo.o: moo.c moo.h
        gcc -g -c moo.o moo.c

loo.o: loo.c loo.h
        gcc -g -c loo.o loo.c

foo: foo.o
	gcc -o foo foo.o

goo: goo.o
    gcc -o goo goo.o

moo: moo.o
    gcc -o moo moo.o

loo: loo.o
    gcc -o loo loo.o

foo.c: foo.y
	yacc -o foo.c foo.y

goo.c: goo.y
    yacc -o goo.c goo.y

moo.c: moo.y
    yacc -o moo.c moo.y

loo.c: loo.y
    yacc -o loo.c loo.y