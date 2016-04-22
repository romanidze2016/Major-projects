VERSION 5.00
Begin VB.Form frmGameOver 
   BackColor       =   &H0080FF80&
   Caption         =   "Snake"
   ClientHeight    =   3255
   ClientLeft      =   120
   ClientTop       =   465
   ClientWidth     =   5925
   LinkTopic       =   "Form2"
   ScaleHeight     =   3255
   ScaleWidth      =   5925
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdMenu 
      BackColor       =   &H0080FFFF&
      Caption         =   "Main Menu"
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   15.75
         Charset         =   204
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   2880
      Style           =   1  'Graphical
      TabIndex        =   4
      Top             =   2640
      Width           =   1815
   End
   Begin VB.CommandButton cmdRestart 
      BackColor       =   &H0080FFFF&
      Caption         =   "Restart"
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   15.75
         Charset         =   204
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   1200
      Style           =   1  'Graphical
      TabIndex        =   3
      Top             =   2640
      Width           =   1455
   End
   Begin VB.Label lblScore 
      Alignment       =   2  'Center
      BackColor       =   &H0080FF80&
      Caption         =   "1045"
      BeginProperty Font 
         Name            =   "Arial Rounded MT Bold"
         Size            =   15.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H000000C0&
      Height          =   495
      Left            =   1920
      TabIndex        =   2
      Top             =   1680
      Width           =   2175
   End
   Begin VB.Label lblYourScore 
      Alignment       =   2  'Center
      BackColor       =   &H0080FF80&
      Caption         =   "Your score:"
      BeginProperty Font 
         Name            =   "Arial Rounded MT Bold"
         Size            =   18
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H000000C0&
      Height          =   495
      Left            =   1440
      TabIndex        =   1
      Top             =   1080
      Width           =   3135
   End
   Begin VB.Label lblOver 
      Alignment       =   2  'Center
      BackColor       =   &H0080FF80&
      Caption         =   "Game Over"
      BeginProperty Font 
         Name            =   "Snap ITC"
         Size            =   21.75
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H000000FF&
      Height          =   735
      Left            =   1320
      TabIndex        =   0
      Top             =   240
      Width           =   3255
   End
End
Attribute VB_Name = "frmGameOver"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub cmdMenu_Click()
frmMenu.Show
Unload Me
End Sub

Private Sub cmdRestart_Click()
frmGame.Show
Unload Me
End Sub

Private Sub Form_Load()
Unload frmGame
End Sub
