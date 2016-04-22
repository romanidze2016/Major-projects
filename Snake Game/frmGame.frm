VERSION 5.00
Begin VB.Form frmGame 
   BackColor       =   &H00FFFFFF&
   Caption         =   "Snake"
   ClientHeight    =   5385
   ClientLeft      =   225
   ClientTop       =   870
   ClientWidth     =   6495
   LinkTopic       =   "Form1"
   Picture         =   "frmGame.frx":0000
   ScaleHeight     =   5385
   ScaleWidth      =   6495
   StartUpPosition =   3  'Windows Default
   Begin VB.Timer tmrBigFood 
      Enabled         =   0   'False
      Interval        =   85
      Left            =   5760
      Top             =   3240
   End
   Begin VB.Timer tmrDown 
      Enabled         =   0   'False
      Interval        =   85
      Left            =   5280
      Top             =   2160
   End
   Begin VB.Timer tmrLeft 
      Enabled         =   0   'False
      Interval        =   85
      Left            =   5280
      Top             =   1680
   End
   Begin VB.Timer tmrRight 
      Enabled         =   0   'False
      Interval        =   85
      Left            =   5280
      Top             =   720
   End
   Begin VB.Timer tmrUp 
      Interval        =   85
      Left            =   5280
      Top             =   1200
   End
   Begin VB.Label Label4 
      BackColor       =   &H00000000&
      Caption         =   "Label4"
      Height          =   615
      Index           =   1
      Left            =   3720
      TabIndex        =   16
      Top             =   4800
      Width           =   15
   End
   Begin VB.Label Label4 
      BackColor       =   &H00000000&
      Caption         =   "Label4"
      Height          =   615
      Index           =   0
      Left            =   1800
      TabIndex        =   15
      Top             =   4800
      Width           =   15
   End
   Begin VB.Label Label3 
      BackColor       =   &H00000000&
      Caption         =   "Label3"
      Height          =   15
      Left            =   0
      TabIndex        =   14
      Top             =   4800
      Width           =   6495
   End
   Begin VB.Label lblPressPause 
      Alignment       =   2  'Center
      BackColor       =   &H0080FFFF&
      Caption         =   "Press SPACE to pause the game"
      BeginProperty Font 
         Name            =   "Tempus Sans ITC"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H000000C0&
      Height          =   495
      Left            =   3960
      TabIndex        =   13
      Top             =   4920
      Width           =   2175
   End
   Begin VB.Image imgApple 
      Height          =   255
      Left            =   720
      Picture         =   "frmGame.frx":771C
      Stretch         =   -1  'True
      Top             =   1200
      Width           =   255
   End
   Begin VB.Image imgMouse 
      Height          =   255
      Left            =   720
      Picture         =   "frmGame.frx":C650
      Stretch         =   -1  'True
      Top             =   1560
      Visible         =   0   'False
      Width           =   495
   End
   Begin VB.Label lblBest 
      BackColor       =   &H0080FFFF&
      Caption         =   "0"
      BeginProperty Font 
         Name            =   "Arial"
         Size            =   13.5
         Charset         =   204
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00000000&
      Height          =   375
      Left            =   3000
      TabIndex        =   12
      Top             =   4920
      Width           =   855
   End
   Begin VB.Label lblScore 
      BackColor       =   &H0080FFFF&
      Caption         =   "0"
      BeginProperty Font 
         Name            =   "Arial"
         Size            =   13.5
         Charset         =   204
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00000000&
      Height          =   375
      Left            =   1200
      TabIndex        =   11
      Top             =   4920
      Width           =   615
   End
   Begin VB.Label lblBestBox 
      BackColor       =   &H0080FFFF&
      Caption         =   "Best :"
      BeginProperty Font 
         Name            =   "Arial Rounded MT Bold"
         Size            =   14.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H000000C0&
      Height          =   495
      Left            =   2040
      TabIndex        =   10
      Top             =   4920
      Width           =   855
   End
   Begin VB.Label lblScoreBox 
      BackColor       =   &H0080FFFF&
      Caption         =   "Score:"
      BeginProperty Font 
         Name            =   "Arial Rounded MT Bold"
         Size            =   14.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H000000C0&
      Height          =   375
      Left            =   120
      TabIndex        =   9
      Top             =   4920
      Width           =   975
   End
   Begin VB.Label lblBorder 
      BackColor       =   &H0080FFFF&
      ForeColor       =   &H00C0C0C0&
      Height          =   1095
      Left            =   0
      TabIndex        =   8
      Top             =   4800
      Width           =   6495
   End
   Begin VB.Label lblPause 
      Alignment       =   2  'Center
      Caption         =   "Pause"
      BeginProperty Font 
         Name            =   "Arial"
         Size            =   15
         Charset         =   204
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   2400
      TabIndex        =   7
      Top             =   2160
      Visible         =   0   'False
      Width           =   1575
   End
   Begin VB.Label lblSnake 
      BackColor       =   &H00008000&
      BorderStyle     =   1  'Fixed Single
      Height          =   255
      Index           =   6
      Left            =   2880
      TabIndex        =   6
      Top             =   4560
      Width           =   255
   End
   Begin VB.Label lblSnake 
      BackColor       =   &H00008000&
      BorderStyle     =   1  'Fixed Single
      Height          =   255
      Index           =   5
      Left            =   2880
      TabIndex        =   5
      Top             =   4320
      Width           =   255
   End
   Begin VB.Label lblHead 
      BackColor       =   &H00000000&
      BorderStyle     =   1  'Fixed Single
      Height          =   255
      Left            =   2880
      TabIndex        =   4
      Top             =   3120
      Width           =   255
   End
   Begin VB.Label lblSnake 
      BackColor       =   &H00008000&
      BorderStyle     =   1  'Fixed Single
      Height          =   255
      Index           =   4
      Left            =   2880
      TabIndex        =   3
      Top             =   4080
      Width           =   255
   End
   Begin VB.Label lblSnake 
      BackColor       =   &H00008000&
      BorderStyle     =   1  'Fixed Single
      Height          =   255
      Index           =   3
      Left            =   2880
      TabIndex        =   2
      Top             =   3840
      Width           =   255
   End
   Begin VB.Label lblSnake 
      BackColor       =   &H00008000&
      BorderStyle     =   1  'Fixed Single
      Height          =   255
      Index           =   2
      Left            =   2880
      TabIndex        =   1
      Top             =   3600
      Width           =   255
   End
   Begin VB.Label lblSnake 
      BackColor       =   &H00008000&
      BorderStyle     =   1  'Fixed Single
      Height          =   255
      Index           =   1
      Left            =   2880
      TabIndex        =   0
      Top             =   3360
      Width           =   255
   End
   Begin VB.Menu mnuGame 
      Caption         =   "&Game"
      Begin VB.Menu mnuNewGame 
         Caption         =   "&New Game"
      End
      Begin VB.Menu mnuMainMenu 
         Caption         =   "&Main Menu"
      End
      Begin VB.Menu mnuExit 
         Caption         =   "&Exit"
      End
   End
   Begin VB.Menu mnuSpeed 
      Caption         =   "&Speed"
      Begin VB.Menu mnuSlow 
         Caption         =   "&Slow"
      End
      Begin VB.Menu mnuMedium 
         Caption         =   "&Medium"
      End
      Begin VB.Menu mnuFast 
         Caption         =   "&Fast"
      End
   End
End
Attribute VB_Name = "frmGame"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit
Dim snakesize As Integer 'number of parts in snake's body
Dim timer As Integer 'this number represents the timer, which was enabled before the game was paused, so after pressing space again the right timer is enabled
Dim space As Boolean 'tells the program if space button has alredy been pressed, so it could either pause or unpause the game correctly
Dim bigpiece As Integer 'this variable is used to count the number of apples eaten. Thus when 6 apples are eaten mouse appears and the variable becomes equal to 0 again
Dim bigtop As Integer 'top coordinates of mouse
Dim bigleft As Integer 'coordinates of left border of mouse
Dim bigleft2 As Integer 'coordinates of right border of mouse
Dim bigtmrcounter As Integer 'this variable is used in tmrBigFood. When it is equal to 30 timer stops, makes mouse invisible and apple visible again.
Dim placeobject, crashed, finished As Boolean 'finished variable is used to ensure that the timer, which has just been enabled, has moved the snake at least once.
'If the variable is not true next timer does not get enabled. This helps to prevent snake from hiting itsself when buttons are pressed too fast.
Dim bonusformouse, bonusforapple As Integer 'the values which are added to the score when either mouse or apple is eaten
Dim bestscore As String
Dim score As Integer

Private Sub Form_KeyDown(KeyCode As Integer, Shift As Integer)
'When arrow key is pressed this sub program enables one of the timers and disables other timers,
'which move snake in different directions. Thus, user is able to the direction in which the snake will move
Select Case KeyCode
 Case vbKeyUp
     If tmrDown.Enabled = False And space = False And finished = True Then
        finished = False
        tmrUp.Enabled = True
        tmrRight.Enabled = False
        tmrLeft.Enabled = False
        tmrDown.Enabled = False
    End If
 Case vbKeyDown
    If tmrUp.Enabled = False And space = False And finished = True Then
        finished = False
        tmrUp.Enabled = False
        tmrRight.Enabled = False
        tmrLeft.Enabled = False
        tmrDown.Enabled = True
    End If
 Case vbKeyRight
    If tmrLeft.Enabled = False And space = False And finished = True Then
        finished = False
        tmrUp.Enabled = False
        tmrRight.Enabled = True
        tmrLeft.Enabled = False
        tmrDown.Enabled = False
    End If
 Case vbKeyLeft
     If tmrRight.Enabled = False And space = False And finished = True Then
        finished = False
        tmrUp.Enabled = False
        tmrRight.Enabled = False
        tmrLeft.Enabled = True
        tmrDown.Enabled = False
    End If
'If space is pressed the sub program pauses the game
'or continues it depending on whether space has already been pressed.
 Case vbKeySpace
    Select Case space
    Case False
        lblPause.Visible = True
        tmrUp.Enabled = False
        tmrRight.Enabled = False
        tmrLeft.Enabled = False
        tmrDown.Enabled = False
        If tmrBigFood.Enabled = True Then
        tmrBigFood.Enabled = False
        End If
        space = True
    Case True
        space = False
        lblPause.Visible = False
        If bigpiece = 6 Then
        tmrBigFood.Enabled = True
        End If
        Select Case timer
            Case 1
                tmrUp.Enabled = True
            Case 2
                tmrRight.Enabled = True
            Case 3
                tmrLeft.Enabled = True
            Case 4
                tmrDown.Enabled = True
        End Select
    End Select
End Select
End Sub

Private Sub Form_Load()
Randomize timer
crashed = False
snakesize = 6
bonusforapple = 10
bonusformouse = 72
Open App.Path & "\Record.txt" For Input As #1
    Input #1, bestscore
    lblBest.Caption = bestscore
Close #1
finished = False
End Sub

Private Sub mnuExit_Click()
End
End Sub

Private Sub mnuFast_Click()
tmrUp.Interval = 60
tmrLeft.Interval = 60
tmrRight.Interval = 60
tmrDown.Interval = 60
tmrBigFood.Interval = 60
bonusforapple = 15
bonusformouse = 92
Call newgame
End Sub

Private Sub mnuMainMenu_Click()
frmMenu.Show
End Sub

Private Sub mnuMedium_Click()
tmrUp.Interval = 85
tmrLeft.Interval = 85
tmrRight.Interval = 85
tmrDown.Interval = 85
tmrBigFood.Interval = 85
bonusforapple = 10
bonusformouse = 72
Call newgame
End Sub

Private Sub mnuNewGame_Click()
'Initially I used "Call endgame" in order to start a new game, but instead,
'the whole program just dissapeared. The reason is probably somewhere in the part of the code which is connected to frmGameOver.
'That is why I created a new sub called newgame, which contains only the last half of endgame sub
Call newgame
End Sub

Private Sub mnuSlow_Click()
tmrUp.Interval = 145
tmrLeft.Interval = 145
tmrRight.Interval = 145
tmrDown.Interval = 145
tmrBigFood.Interval = 145
bonusforapple = 5
bonusformouse = 52
Call newgame
End Sub

Private Sub tmrUp_Timer()
'This timer and other timers, which are tmrDown,tmrLeft and tmrRight, are responsible for snake's movement.
'All of them move snake's head in different directions and place pieces of snake's body in the position of the piece or head, which is in front of them.
'Thus, all pieces of snake's body follow the same path as snake's head.
Dim counter As Integer
If timer <> 1 Then
    timer = 1
End If
For counter = snakesize To 2 Step -1
    lblSnake(counter).Top = lblSnake(counter - 1).Top
    lblSnake(counter).Left = lblSnake(counter - 1).Left
    lblSnake(counter).BackColor = lblSnake(counter - 1).BackColor
Next
lblSnake(1).Top = lblHead.Top
lblSnake(1).Left = lblHead.Left
lblHead.Top = lblHead.Top - 240
Call movesnake
Call checksnake
Select Case bigpiece
    Case Is < 6
        tmrBigFood.Enabled = False
        Call addobject
    Case Is = 6
        Call bigfood
End Select
finished = True
End Sub

Private Sub tmrRight_Timer()
Dim counter As Integer
If timer <> 2 Then
    timer = 2
End If
For counter = snakesize To 2 Step -1
    lblSnake(counter).Top = lblSnake(counter - 1).Top
    lblSnake(counter).Left = lblSnake(counter - 1).Left
    lblSnake(counter).BackColor = lblSnake(counter - 1).BackColor
Next
lblSnake(1).Top = lblHead.Top
lblSnake(1).Left = lblHead.Left
lblHead.Left = lblHead.Left + 240
Call movesnake
Call checksnake
Select Case bigpiece
    Case Is < 6
        tmrBigFood.Enabled = False
        Call addobject
    Case Is = 6
        Call bigfood
End Select
finished = True
End Sub

Private Sub tmrLeft_Timer()
Dim counter As Integer
If timer <> 3 Then
    timer = 3
End If
For counter = snakesize To 2 Step -1
    lblSnake(counter).Top = lblSnake(counter - 1).Top
    lblSnake(counter).Left = lblSnake(counter - 1).Left
    lblSnake(counter).BackColor = lblSnake(counter - 1).BackColor
Next
lblSnake(1).Top = lblHead.Top
lblSnake(1).Left = lblHead.Left
lblHead.Left = lblHead.Left - 240
Call movesnake
Call checksnake
Select Case bigpiece
    Case Is < 6
        tmrBigFood.Enabled = False
        Call addobject
    Case Is = 6
        Call bigfood
End Select
finished = True
End Sub

Private Sub tmrDown_Timer()
Dim counter As Integer
If timer <> 4 Then
    timer = 4
End If
For counter = snakesize To 2 Step -1
    lblSnake(counter).Top = lblSnake(counter - 1).Top
    lblSnake(counter).Left = lblSnake(counter - 1).Left
    lblSnake(counter).BackColor = lblSnake(counter - 1).BackColor
Next
lblSnake(1).Top = lblHead.Top
lblSnake(1).Left = lblHead.Left
lblHead.Top = lblHead.Top + 240
Call movesnake
Call checksnake
Select Case bigpiece
    Case Is < 6
        tmrBigFood.Enabled = False
        Call addobject
    Case Is = 6
        Call bigfood
End Select
finished = True
End Sub

Private Sub checksnake()
'This sub checks if the snake has hit itsself
'When snake's head in in the same position with any piece of its body the game ends.
Dim snakepiece As Integer
For snakepiece = 1 To snakesize
    If lblHead.Top = lblSnake(snakepiece).Top And lblHead.Left = lblSnake(snakepiece).Left Then
        crashed = True
        score = Val(lblScore.Caption)
    End If
Next
If crashed = True Then
    Call endgame
    frmGameOver.Show
End If
End Sub

Private Sub movesnake()
'This sub shifts snake's head to an opposite side of the window when it hits one of the walls.
'As a result snake does not intersect window's borders as well.
Select Case lblHead.Left
    Case Is > 6240
        lblHead.Left = 0
    Case Is < 0
        lblHead.Left = 6240
End Select
Select Case lblHead.Top
    Case Is > 4560
        lblHead.Top = 0
    Case Is < 0
        lblHead.Top = 4560
End Select
End Sub

Private Sub addobject()
'When snake's head is in the same position with apple this sub places the apple in a new position, adds
'points to the score and also adds a new piece to the snake' body
If lblHead.Top = imgApple.Top And lblHead.Left = imgApple.Left Then
    lblScore.Caption = Val(lblScore.Caption) + bonusforapple
    Load lblSnake(snakesize + 1)
    With lblSnake(snakesize + 1)
        .Visible = True
        .Top = lblSnake(snakesize).Top
        .Left = lblSnake(snakesize).Left
    End With
    snakesize = snakesize + 1
    'This information goes to timers, which secure snake's movement and direction.
    'When bigpiece is equal to 6, enabled timer calls BigFood sub.
    bigpiece = bigpiece + 1
    If bigpiece = 6 Then
        imgApple.Visible = False
    End If
    'This loop ensures that apple does not appear on snake's body or head
    Do
        placeobject = True
        imgApple.Top = Int(Rnd * 4561)
        'imgApple.top mod (240) and imgApple.left mod (240) have to be equal to 0 so that apple is on the same level
        'as snake's head,which makes it possible for the apple to be at the same position. If they were on different levels
        'they would be very unlikely to meet each other
        While imgApple.Top Mod (240) <> 0
            imgApple.Top = imgApple.Top + 1
        Wend
        imgApple.Left = Int(Rnd * 6241)
        While imgApple.Left Mod (240) <> 0
            imgApple.Left = imgApple.Left + 1
        Wend
        checkapple
    Loop Until placeobject = True
End If
End Sub

Private Sub tmrBigFood_Timer()
'This timer counts how much time has passed since the mouse appeared on the screen.
'When the mouse is eaten or bigtmrcounter is equal to 30 mouse disappears from the screen.
'Also the timer subtracts 1 from bonusformouse everytime it operates. That is why the longer it has
'been since the mouse appeared, the less points the player will get for eating the mouse
Select Case bigtmrcounter
    Case Is < 30
        bigtmrcounter = bigtmrcounter + 1
        bonusformouse = bonusformouse - 1
        imgMouse.Visible = True
    Case Is >= 30
        imgMouse.Visible = False
        imgApple.Visible = True
        bigpiece = 0
        bigtmrcounter = 0
        bonusformouse = 72
End Select
End Sub

Private Sub bigfood()
'When 6 apples are eaten, this sub enables tmrBigFood and gives the mouse new coordinates.
'Also it checks if the snake ate the mouse. When snake eats the mouse, this sub disables the timer, adds some points to the score and
'makes the mouse invisible and the apple visible.
If bigtmrcounter = 0 Then
    Do
        placeobject = True
        bigtop = Int(Rnd * 4561)
        bigleft = Int(Rnd * 6001)
        While bigtop Mod (240) <> 0
            bigtop = bigtop + 1
        Wend
        While bigleft Mod (240) <> 0
            bigleft = bigleft + 1
        Wend
        bigleft2 = bigleft + 240
        Call checkmouse
    Loop Until placeobject = True
    imgMouse.Top = bigtop
    imgMouse.Left = bigleft
    tmrBigFood.Enabled = True
End If
If lblHead.Top = bigtop And lblHead.Left >= bigleft And lblHead.Left <= bigleft2 Then
    tmrBigFood.Enabled = False
    imgMouse.Visible = False
    imgApple.Visible = True
    bigpiece = 0
    bigtmrcounter = 0
    lblScore.Caption = Val(lblScore.Caption) + bonusformouse
    bonusformouse = 72
End If
End Sub

Private Sub checkapple()
'When apple's position is the same with any piece of snake or its head, the loop in addobject sub
'randomizes new coordinates for apple.
Dim counter As Integer
If lblHead.Top = imgApple.Top And lblHead.Left = imgApple.Left Then
    placeobject = False
End If
For counter = 1 To snakesize
    If lblSnake(counter).Top = imgApple.Top And lblSnake(counter).Left = imgApple.Left Then
        placeobject = False
    End If
Next
End Sub

Private Sub checkmouse()
'This sub does the same thing as checkapple does, but only for mouse. Mouse object is
'bigger than apple object, so the code is a bit different
Dim counter As Integer
If lblHead.Top = bigtop And lblHead.Left >= bigleft And lblHead.Left <= bigleft2 Then
    placeobject = False
End If
For counter = 1 To snakesize
    If lblSnake(counter).Top = bigtop And lblSnake(counter).Left >= bigleft And lblSnake(counter).Left <= bigleft2 Then
        placeobject = False
    End If
Next
End Sub

Private Sub endgame()
'This sub simply ends the game. First, it checks if the score is higher than the record then
'if it is, it saves the score to Record.txt file. Then the sub loads frmGameOver with the score displayed.
'Eventually, the sub calls newgame sub in order to unload all extra pieces of the snake and set everything
'to initial values
Dim counter As Integer
If score > Val(lblBest.Caption) Then
    lblBest.Caption = score
    Open App.Path & "\Record.txt" For Output As #1
        Write #1, score
    Close #1
    frmGameOver.lblYourScore.Caption = "New Record!"
End If
frmGameOver.lblScore.Caption = score
Call newgame
End Sub

Private Sub newgame()
Dim counter As Integer
score = 0
lblScore.Caption = score
For counter = snakesize To 7 Step -1
    Unload lblSnake(counter)
Next
snakesize = 6
crashed = False
lblHead.Left = Int(Rnd * 6241)
lblHead.Top = Int(Rnd * 4561)
While lblHead.Top Mod (240) <> 0
    lblHead.Top = lblHead.Top + 1
Wend
While lblHead.Left Mod (240) <> 0
    lblHead.Left = lblHead.Left + 1
Wend
bigpiece = 0
bonusformouse = 72
tmrBigFood.Enabled = False
bigtmrcounter = 0
imgApple.Visible = True
imgMouse.Visible = False
End Sub

