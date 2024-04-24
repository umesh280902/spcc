%{

void display(int);

%}


%%
[a|e|i|o|u]+ {

	int flag=1;
	display(flag);
	}
	
	.+ {
	
		int flag=0;
		display(flag);
		}
		
%%

void display(int flag)

{
	if (flag==0)
		printf("\nThe given word is not a vowel\n");
		
	else
		printf("\nThe given word is  a vowel\n");
		
}

void main()
{

	printf("\n Enter something to check whether it is a vowel or not \n");
	yylex();
}
					