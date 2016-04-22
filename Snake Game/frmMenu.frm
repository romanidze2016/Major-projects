VERSION 5.00
Begin VB.Form frmMenu 
   BackColor       =   &H0080FF80&
   Caption         =   "Snake"
   ClientHeight    =   4200
   ClientLeft      =   120
   ClientTop       =   465
   ClientWidth     =   6540
   LinkTopic       =   "Form3"
   ScaleHeight     =   4200
   ScaleWidth      =   6540
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdAbout 
      BackColor       =   &H0080FFFF&
      Caption         =   "About"
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   15.75
         Charset         =   204
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   495
      Left            =   2040
      Style           =   1  'Graphical
      TabIndex        =   4
      Top             =   3000
      Width           =   2295
   End
   Begin VB.CommandButton cmdExit 
      BackColor       =   &H0080FFFF&
      Caption         =   "Exit"
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   15.75
         Charset         =   204
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   495
      Left            =   2040
      Style           =   1  'Graphical
      TabIndex        =   3
      Top             =   3600
      Width           =   2295
   End
   Begin VB.CommandButton cmdRecord 
      BackColor       =   &H0080FFFF&
      Caption         =   "Record"
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   15.75
         Charset         =   204
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   495
      Left            =   2040
      Style           =   1  'Graphical
      TabIndex        =   2
      Top             =   2400
      Width           =   2295
   End
   Begin VB.CommandButton cmdStart 
      BackColor       =   &H0080FFFF&
      Caption         =   "Start"
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   15.75
         Charset         =   204
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   495
      Left            =   2040
      MaskColor       =   &H00800000&
      Style           =   1  'Graphical
      TabIndex        =   1
      Top             =   1800
      Width           =   2295
   End
   Begin VB.Label lblHeading 
      Alignment       =   2  'Center
      BackColor       =   &H000000FF&
      Caption         =   "Snake"
      BeginProperty Font 
         Name            =   "Script MT Bold"
         Size            =   48
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H0000C000&
      Height          =   1335
      Left            =   1080
      TabIndex        =   0
      Top             =   240
      Width           =   4335
   End
End
Attribute VB_Name = "frmMenu"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub cmdAbout_Click()
frmAbout.Show
End Sub

Private Sub cmdExit_Click()
End
End Sub

Private Sub cmdRecord_Click()
frmRecord.Show
End Sub

Private Sub cmdStart_Click()
frmGame.Show
Unload Me
End Sub

Private Sub Form_Load()
Unload frmGame
End Sub

