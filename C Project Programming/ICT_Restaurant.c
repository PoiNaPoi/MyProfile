#include<stdio.h>
#include<stdbool.h>
#include<windows.h>
#include<time.h>

static int table[10]={1,1,1,1,1,1,1,1,1,1},food[10][4]={{0,0,0,0}},drink[10][3]={{0,0,0}};

void menu(int *n){
	int input;
	printf("ICT Restaurant System\n");
	printf("============================================================\n");
	printf("[1] Book a table\n");
	printf("[2] Order food & drink\n");
	printf("[3] Display and clear a bill\n");
	printf("[4] Status of tables\n");
	printf("[0] Exit\n");
	printf("------------------------------------------------------------\n");
	menu:{
		printf("Enter the choice: ");
		scanf("%d",&input);
	}
	if(input==0||input==1||input==2||input==3||input==4) *n = input;
	else{
		fflush(stdin);
		goto menu;
	}
}

int book_table(void){
	int num_man,i,chose;
	int seat[10]={2,2,2,2,4,4,4,4,8,8};
	printf("[Book a table]\n\n");
	go:{
		fflush(stdin);
		printf("Enter the number of people: ");
		scanf("%d",&num_man);
	}
	if(num_man<=0){
		printf("Invalid number of people\n");
		goto go;
	}
	printf("============================================================\n");
	printf("List of tables\t\t\t\tStatus\n");
	printf("============================================================\n");
	for(i=0;i<10;i++){
		printf("Table %d: %d seats\t\t\t",i+1,seat[i]);
		if(table[i]==1&&num_man<=seat[i])	printf("Available\n");
		else if(table[i]==1&&num_man>seat[i])	printf("Not enough seat\n");
		else printf("Occupied\n");
	}
	printf("\n");
	run:{
		printf("Enter a table number (input 0 to cancel): ");
		scanf("%d",&chose);
	}
	
	if(chose<=10&&chose>0){
		if(table[chose-1]==1&&num_man<=seat[chose-1]){
			printf("The seat is update!");
			return table[chose-1]++;
		}
		else{
			printf("Please enter the available table.\n");
			fflush(stdin);
			goto run;
		}
	}
	else if(chose==0){
		
	}
	else{
		printf("Invalid choice!\n");
		fflush(stdin);
		goto run;
	}
}

void order(void){
	int i,j,input,cout[10];
	bool c=false;
	int kao,dri;
	char con;
	int food1[10][4]={{0,0,0,0}},drink1[10][3]={{0,0,0}};
	printf("[Order food & drink]\n\n");
	printf("List of occupied tables:\n");
	for(j=0,i=0;i<10;i++){
		if(table[i]==2){
			printf("Table %d\n",i+1);
			cout[j]=i+1;
			j++;
		}	
	}
	if(j==0) printf("There is no one sitting at the table.\nPress Enter to go back to the main menu.\n");
	printf("\n");
	run:{
		fflush(stdin);
		printf("Enter the table number [1-10] (0 to exit): ");
		scanf("%d",&input);
	}
	if(input>0&&input<=10){
		for(i=0;i<j;i++){
			if(input==cout[i]){
				c = true;
			}
		}
		if(c==true){
			printf("\n============================================================\n");
			printf("Order food & drink\n");
			printf("============================================================\n");
			printf("\nFood Menu\t\t\t\tPrice (Baht)\n");
			printf("------------------------------------------------------------\n");
			printf("[1] Kao Pad Kra Pao\t\t\t45.0\n");
			printf("[2] Fried Rice\t\t\t\t45.0\n");
			printf("[3] Kha Nar Mhoo Krob\t\t\t50.0\n");
			printf("[4] Tom Yum Koong Nam Khont\t\t60.0\n");
			printf("------------------------------------------------------------\n");
			run2:{
				fflush(stdin);
				printf("Enter the choice (input 0 to stop): ");
				scanf("%d",&kao);
			}
			switch(kao){
				case 1:{
					food1[input-1][0]++;
					goto run2;
					break;
				}
				case 2:{
					food1[input-1][1]++;
					goto run2;
					break;
				}
				case 3:{
					food1[input-1][2]++;
					goto run2;
					break;
				}
				case 4:{
					food1[input-1][3]++;
					goto run2;
					break;
				}
				case 0:{
					
					break;
				}
				default:{
					printf("Invalid choice!\n");
					goto run2;
					break;
				}
			}
			printf("\nDrink Menu\t\t\t\tPrice (Baht)\n");
			printf("------------------------------------------------------------\n");
			printf("[1] Coca Cola\t\t\t\t20.0\n");
			printf("[2] Orange Juice\t\t\t30.0\n");
			printf("[3] Still Water\t\t\t\t10.0\n");
			printf("------------------------------------------------------------\n");
			run3:{
				fflush(stdin);
				printf("Enter the choice (input 0 to stop): ");
				scanf("%d",&dri);
			}
			switch(dri){
				case 1:{
					drink1[input-1][0]++;
					goto run3;
					break;
				}
				case 2:{
					drink1[input-1][1]++;
					goto run3;
					break;
				}
				case 3:{
					drink1[input-1][2]++;
					goto run3;
					break;
				}
				case 0:{
					
					break;
				}
				default:{
					printf("Invalid choice!\n");
					goto run3;
					break;
				}
			}
			printf("\nYou have order the following:\n");
			if(food1[input-1][0]!=0)	printf("[F] Kao Pad Kra Pao x%d\n",food1[input-1][0]);
			if(food1[input-1][1]!=0)	printf("[F] Fried Rice x%d\n",food1[input-1][1]);
			if(food1[input-1][2]!=0)	printf("[F] Kha Nar Mhoo Krob x%d\n",food1[input-1][2]);
			if(food1[input-1][3]!=0)	printf("[F] Tom Yum Koong Nam Khon x%d\n",food1[input-1][3]);
			if(drink1[input-1][0]!=0) printf("[D] Coca Cola x%d\n",drink1[input-1][0]);
			if(drink1[input-1][1]!=0) printf("[D] Orange Juice x%d\n",drink1[input-1][1]);
			if(drink1[input-1][2]!=0) printf("[D] Still Water x%d\n",drink1[input-1][2]);
			run4:{
				fflush(stdin);
				printf("Confirm? (y|n): ");
				scanf("%c",&con);
			}
			if(con=='y'||con=='Y'){
				food[input-1][0]+=food1[input-1][0];
				food[input-1][1]+=food1[input-1][1];
				food[input-1][2]+=food1[input-1][2];
				food[input-1][3]+=food1[input-1][3];
				drink[input-1][0]+=drink1[input-1][0];
				drink[input-1][1]+=drink1[input-1][1];
				drink[input-1][2]+=drink1[input-1][2];
				return;
			}
			else if(con=='n'||con=='N'){
				return;
			}
			else{
				goto run4;
			}
		}
		else{
			fflush(stdin);
			goto run;
		}
	}
	else if(input==0){
		return;
	}
	else{
		fflush(stdin);
		goto run;
	}
}

void bill(void){
	int i,j,cout[10],input;
	bool c;
	char ch;
	printf("[Display and clear a bill]\n\n");
	printf("List of occupied tables:\n");
	for(j=0,i=0;i<10;i++){
		if(table[i]==2){
			printf("Table %d\n",i+1);
			cout[j]=i+1;
			j++;
		}
	}
	if(j==0) printf("There is no one sitting at the table.\nPress Enter to go back to the main menu.\n");
	printf("\n");
	run:{
		fflush(stdin);
		printf("Enter the table number [1-10] (0 to exit): ");
		scanf("%d",&input);
	}
	if(input>0&&input<=10){
		for(i=0;i<j;i++){
			if(input==cout[i]){
				c = true;
			}
		}
		if(c==true){
			ttime();
			printf("\nYou have order the following:\n");
			printf("Food Menu\t\t\tQty.\tPrice (Baht)\n");
			printf("------------------------------------------------------------\n");
			if(food[input-1][0]!=0)	printf("[1] Kao Pad Kra Pao\t\t%d\t%.1f\n",food[input-1][0],45.0*food[input-1][0]);
			if(food[input-1][1]!=0)	printf("[2] Fried Rice\t\t\t%d\t%.1f\n",food[input-1][1],45.0*food[input-1][0]);
			if(food[input-1][2]!=0)	printf("[3] Kha Nar Mhoo Krob\t\t%d\t%.1f\n",food[input-1][2],50.0*food[input-1][0]);
			if(food[input-1][3]!=0)	printf("[4] Tom Yum Koong Nam Khon0\t%d\t%.1f\n",food[input-1][3],60.0*food[input-1][0]);
			if(food[input-1][0]==0&&food[input-1][1]==0&&food[input-1][2]==0&&food[input-1][3]==0)	printf("None\n");
			printf("------------------------------------------------------------\n\n");
			printf("Drink Menu\t\t\tQty.\tPrice (Baht)\n");
			printf("------------------------------------------------------------\n");
			if(drink[input-1][0]!=0)	printf("[1] Coca Cola\t\t\t%d\t%.1f\n",drink[input-1][0],20.0*drink[input-1][0]);
			if(drink[input-1][1]!=0)	printf("[2] Orange Juice\t\t%d\t%.1f\n",drink[input-1][1],30.0*drink[input-1][0]);
			if(drink[input-1][2]!=0)	printf("[3] Still Water\t\t\t%d\t%.1f\n",drink[input-1][2],10.0*drink[input-1][0]);
			if(drink[input-1][0]==0&&drink[input-1][1]==0&&drink[input-1][2]==0)	printf("None\n");
			printf("------------------------------------------------------------\n\n");
			float total=(food[input-1][0]*45)+(food[input-1][1]*45)+(food[input-1][2]*50)+(food[input-1][3]*60)+(drink[input-1][0]*20)+(drink[input-1][1]*30)+(drink[input-1][2]*10);
			float totaldis;
			printf("** Total amount: %.2f\n\n",total);
			run2:{
				fflush(stdin);
				printf("Do you want to clear the bill? (y|n): ");
				scanf("%c",&ch);
			}
			if(ch=='Y'||ch=='y'){
			if(total>0){
				run3:{
					fflush(stdin);
					printf("Member card (y|n): ");
					scanf("%c",&ch);
				}
				if(ch=='y'||ch=='Y'){
					totaldis=total*0.9;
					printf("------------------------------------------------------------\n");
					printf("** Discount : %.2f\n",total*0.1);
					printf("** Total price : %.2f\n",totaldis);
				}
				else if(ch=='n'||ch=='N'){
					printf("------------------------------------------------------------\n");
					totaldis=total;
				}
				else{
					goto run3;
				}
				float pay;
				int p1000=0,p500=0,p100=0,p50=0,p20=0,p10=0,p5=0,p2=0,p1=0,p050=0,p025=0;
				pp:{
					fflush(stdin);
					printf("**** Pay : ");
					scanf("%f",&pay);
				}
				if(pay<totaldis) goto pp;
				while(pay-totaldis>=0.25){
					if(pay-totaldis>=1000){
						p1000++;
						pay-=1000;
					}
					else if(pay-totaldis>=500){
						p500++;
						pay-=500;
					}
					else if(pay-totaldis>=100){
						p100++;
						pay-=100;
					}
					else if(pay-totaldis>=50){
						p50++;
						pay-=50;
					}
					else if(pay-totaldis>=20){
						p20++;
						pay-=20;
					}
					else if(pay-totaldis>=10){
						p10++;
						pay-=10;
					}
					else if(pay-totaldis>=5){
						p5++;
						pay-=5;
					}
					else if(pay-totaldis>=2){
						p2++;
						pay-=2;
					}
					else if(pay-totaldis>=1){
						p1++;
						pay-=1;
					}
					else if(pay-totaldis>=0.5){
						p050++;
						pay-=0.5;
					}
					else if(pay-totaldis>=0.25){
						p025++;
						pay-=0.25;
					}
				}
				printf("============================================================\n");
				printf("Change :\n");
				if(p1000>0) printf("|1000 Baht|\tx%d\n",p1000);
				if(p500>0) printf("|500 Baht|\tx%d\n",p500);
				if(p100>0) printf("|100 Baht|\tx%d\n",p100);
				if(p50>0) printf("|50 Baht|\tx%d\n",p50);
				if(p20>0) printf("|20 Baht|\tx%d\n",p20);
				if(p10>0) printf("|10 Baht|\tx%d\n",p10);
				if(p5>0) printf("|5 Baht|\tx%d\n",p5);
				if(p2>0) printf("|2 Baht|\tx%d\n",p2);
				if(p1>0) printf("|1 Baht|\tx%d\n",p1);
				if(p050>0) printf("|0.50 Baht|\tx%d\n",p050);
				if(p025>0) printf("|0.25 Baht|\tx%d\n",p025);
			}
			else goto run5;
			}
			else if(ch=='N'||ch=='n'){
				return;
			}
			else goto run2;
			int k;
			run5:{
				printf("Please enter 0 to return MENU :");
				scanf("%d",&k);
				fflush(stdin);
			}
			if(k!=0) goto run5;
//			run2:{
//				fflush(stdin);
//				printf("Do you want to clear the bill? (y|n): ");
//				scanf("%c",&ch);
//			}
			if(ch=='y'||ch=='Y'){
				food[input-1][0]=0;
				food[input-1][1]=0;
				food[input-1][2]=0;
				food[input-1][3]=0;
				drink[input-1][0]=0;
				drink[input-1][1]=0;
				drink[input-1][2]=0;
				table[input-1]=1;
				return;
			}
			else if(ch=='n'||ch=='N'){
				return;
			}
			else{
				goto run2;
			}
		}
		else if(input==0){
			return;
		}
		else{
			fflush(stdin);
			goto run;
		}
	}
	else if(input==0){
		return;
	}
	else{
		fflush(stdin);
		goto run;
	}
}

void ttime(void){
	time_t t;
	time(&t);
	struct tm *tmc;
	tmc = localtime(&t);
	printf(" [%d/%d/%d || %d:%d]\n",tmc->tm_mday,tmc->tm_mon,tmc->tm_year+1900,tmc->tm_hour,tmc->tm_min);
	printf("============================================================\n");
}

void status(void){
	int i,j,k,F=1,D=1;
	printf("[Status of tables]\n\n");
	printf("============================================================\n");
	printf("|Tables|\t|Status|\t|Foods|\t|Drinks|\n");
	printf("============================================================\n");
	for(i=0;i<10;i++){
		printf("%4d",i+1);
		if(table[i]==1) printf("\t\tAvailable");
		else printf("\t\tOccupied");
		for(j=0;j<4;j++){
			if(food[i][j]!=0) F=0;
			if(j<3)
			if(drink[i][j]!=0) D=0;
		}
		if(F==0) printf("\t   Y");
		else printf("\t   N");
		if(D==0) printf("\t   Y\n");
		else printf("\t   N\n");
		F=1;
		D=1;
	}
	printf("============================================================\n");
	run1:{
		printf("Please enter 0 to return MENU :");
		scanf("%d",&k);
		fflush(stdin);
	}
	if(k!=0) goto run1;
	
}

int main(void){
	int input1,i,j;
	FILE *fp;
	fp = fopen("data.txt","r");
	if(fp){
		for(i=0;i<10;i++){
			for(j=0;j<4;j++){
				fscanf(fp,"%d ",&food[i][j]);
			}
		}
		for(i=0;i<10;i++){
			for(j=0;j<3;j++){
				fscanf(fp,"%d ",&drink[i][j]);
			}
		}
		for(i=0;i<10;i++){
			fscanf(fp,"%d ",&table[i]);
		}
		printf("\n");
	}
	start:{
		system("cls");
		ttime();
		menu(&input1);
	}
	system("cls");
	run:{
		switch(input1){
			case 1:{
				ttime();
				book_table();
				goto start;
				break;
			}
			case 2:{
				ttime();
				order();
				goto start;
				break;
			}
			case 3:{
				ttime();
				bill();
				goto start;
				break;
			}
			case 4:{
				ttime();
				status();
				goto start;
				break;
			}
		}
	}
	remove("data.txt");
	fp = fopen("data.txt","w");
	for(i=0;i<10;i++){
		for(j=0;j<4;j++){
			fprintf(fp,"%d ",food[i][j]);
		}
	}
	for(i=0;i<10;i++){
		for(j=0;j<3;j++){
			fprintf(fp,"%d ",drink[i][j]);
		}
	}
	for(i=0;i<10;i++){
		fprintf(fp,"%d ",table[i]);
	}
	printf("\n");
	return 0;
}
