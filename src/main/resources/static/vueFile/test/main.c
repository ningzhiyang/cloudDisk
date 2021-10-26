/**************************************************************************************
*		              8*8LED点阵———显示数字实验												  *
实现现象：下载程序后点阵上显示数字0
			
注意事项：一定要将J24短接片短接到GND端。																				  
***************************************************************************************/

#include "reg51.h"			 //此文件中定义了单片机的一些特殊功能寄存器
#include<intrins.h>

typedef unsigned int u16;	  //对数据类型进行声明定义
typedef unsigned char u8;

sbit SRCLK=P3^6;
sbit RCLK=P3^5;
sbit SER=P3^4;
sbit beep=P1^5;	
sbit led=P2^0;		 //将单片机的P0.0端口定义为led

u8 ledduan[]={0x00,0x00,0x3e,0x41,0x41,0x41,0x3e,0x00};
u8 ledwei[]={0x7f,0xbf,0xdf,0xef,0xf7,0xfb,0xfd,0xfe};

sbit LSA=P2^2;
sbit LSB=P2^3;
sbit LSC=P2^4;

u8 code smgduan[17]={0x3f,0x06,0x5b,0x4f,0x66,0x6d,0x7d,0x07,
					0x7f,0x6f,0x77,0x7c,0x39,0x5e,0x79,0x71};//显示0~F的值
/*******************************************************************************
* 函 数 名         : delay
* 函数功能		   : 延时函数，i=1时，大约延时10us
*******************************************************************************/
void delay(u16 i)
{
	while(i--);	
}

/*******************************************************************************
* 函数名         : Hc595SendByte(u8 dat)
* 函数功能		   : 向74HC595发送一个字节的数据
* 输入           : 无
* 输出         	 : 无
*******************************************************************************/
void Hc595SendByte(u8 dat)
{
	u8 a;
	SRCLK=0;
	RCLK=0;
	for(a=0;a<9;a++)
	{
		SER=dat>>7;
		dat<<=1;

		SRCLK=1;
		_nop_();
		_nop_();
		SRCLK=0;	
	}

	RCLK=1;
	_nop_();
	_nop_();
	RCLK=0;
}

void DigDisplay()
{
	u8 i;
	for(i=0;i<8;i++)
	{
		switch(i)	 //位选，选择点亮的数码管，
		{
		   	case(0):
				LSA=1;LSB=1;LSC=1; break;//显示第0位
			case(1):
				LSA=0;LSB=1;LSC=1; break;//显示第1位
			case(2):
				LSA=1;LSB=0;LSC=1; break;//显示第2位
			case(3):	
				LSA=0;LSB=0;LSC=1; break;//显示第3位
			case(4):
				LSA=1;LSB=1;LSC=0; break;//显示第4位
			case(5):
				LSA=0;LSB=1;LSC=0; break;//显示第5位
			case(6):
				LSA=1;LSB=0;LSC=0; break;//显示第6位
			case(7):
				LSA=0;LSB=0;LSC=0; break;//显示第7位	
		}
		P0=smgduan[i];//发送段码
		//delay(100); //间隔一段时间扫描	
		//P0=0x00;//消隐
	}
}

/*******************************************************************************
* 函 数 名       : main
* 函数功能		 : 主函数
* 输    入       : 无
* 输    出    	 : 无
*******************************************************************************/
void fengmingqi()
{
	beep=~beep;
	delay(100);
}
void shumaguan()
{
		led=0;
		delay(450000);
		led=1;
		delay(450000);
}
void UsartInit()
{
	SCON=0X50;			//设置为工作方式1
	TMOD=0X20;			//设置计数器工作方式2
	PCON=0X80;			//波特率加倍
	TH1=0XF3;				//计数器初始值设置，注意波特率是4800的
	TL1=0XF3;
	ES=1;						//打开接收中断
	EA=1;						//打开总中断
	TR1=1;					//打开计数器
}
void a()
{		   u8 i;
	   //DigDisplay();  //数码管显示函数	

		// shumaguan();

		fengmingqi();

		P0=0x7f;
		for(i=0;i<8;i++)
		{
			P0=ledwei[i];		  //位选
			Hc595SendByte(ledduan[i]);	//发送段选数据
		//	delay(100);		   //延时
			Hc595SendByte(0x00);  //消隐
		}
}
void main()
{			
	UsartInit();  //	串口初始化
	while(1)
	{
		a();
	}	
}
/*******************************************************************************
* 函数名         : Usart() interrupt 4
* 函数功能		  : 串口通信中断函数
* 输入           : 无
* 输出         	 : 无
*******************************************************************************/
void Usart() interrupt 4
{
	u8 receiveData;

	receiveData=SBUF;//出去接收到的数据
	RI = 0;//清除接收中断标志位
	if(receiveData == "12 FF FC"){
		a();
	}
	SBUF=receiveData;//将接收到的数据放入到发送寄存器
	while(!TI);			 //等待发送数据完成
	TI=0;						 //清除发送完成标志位
}
