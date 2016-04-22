VERSION 5.00
Begin VB.Form frmRecord 
   BackColor       =   &H0080FF80&
   Caption         =   "Snake"
   ClientHeight    =   2535
   ClientLeft      =   120
   ClientTop       =   465
   ClientWidth     =   4560
   LinkTopic       =   "Form1"
   ScaleHeight     =   2535
   ScaleWidth      =   4560
   StartUpPosition =   3  'Windows Default
   Begin VB.Label lblBeatit 
      Alignment       =   2  'Center
      BackColor       =   &H0080FF80&
      Caption         =   "Try to beat it!"
      BeginProperty Font 
         Name            =   "Palatino Linotype"
         Size            =   14.25
         Charset         =   204
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00FF0000&
      Height          =   495
      Left            =   840
      TabIndex        =   2
      Top             =   1920
      Width           =   2775
   End
   Begin VB.Label lblRecord 
      Alignment       =   2  'Center
      BackColor       =   &H0080FF80&
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
      Height          =   375
      Left            =   1560
      TabIndex        =   1
      Top             =   1320
      Width           =   1455
   End
   Begin VB.Label lblCurrentRecord 
      Alignment       =   2  'Center
      BackColor       =   &H0080FF80&
      Caption         =   "The Highest Score is"
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
      Height          =   855
      Left            =   600
      TabIndex        =   0
      Top             =   240
      Width           =   3375
   End
End
Attribute VB_Name = "frmRecord"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub Form_Load()
Dim record As Integer
Open App.Path & "\Record.txt" For Input As #1
Input #1, record
lblRecord.Caption = record
Close #1
End Sub
