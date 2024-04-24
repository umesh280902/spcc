%{
void display(int);
%}


%%
[a-zA-Z]+ {
	int flag=1;
	display(flag);
	}
	
	 
[0-9]+ {
		int flag=2;
		display(flag);
}		
.+ {
	int flag=3;
	display(flag);
	}		
%%

void display(int flag){
	if (flag==1)
		printf("\nThe given string");
		 
	else if(flag==2)
		printf("\nThe given number ");
		
	else
		printf("The given string is either a digit or a word\n");	
}

void main()
{
	printf("\n Enter a string to check whether it is a word or a digit \n");
	yylex();
}
					