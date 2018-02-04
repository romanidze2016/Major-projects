#pragma once

#include "config.h"
#include "menu.h"
#include "base.h"
#include "ship.h"
#include "swarm.h"
#include "swarminfo.h"
#include <QDialog>
#include <QSoundEffect>
#include <QWidget>
#include <vector>
#include <string>

namespace game {
class GameDialog : public QDialog {
    Q_OBJECT

public:
    GameDialog(QWidget* parent = nullptr);
    void generateAliens(const QList<SwarmInfo>& swarms);
    virtual ~GameDialog();

protected:
    QTimer* timer;
    QString controls = "mouse";
    void resetGame();
    void initializeGame();
    void paintEvent(QPaintEvent* event);
    void paintBullets(QPainter& painter);
    void updateBullets();
    void updateBox();
    void paintSwarm(QPainter& painter, AlienBase*& root);
    void checkSwarmCollisions(AlienBase*& root);
    // ship and swarms
    Ship* ship;
    int livesLeft = 3;
    std::vector<Bullet*> bullets;
    AlienBase* swarms;  // swarms is the ROOT node of the composite
    int aliensLeft;
    int initialNumOfAliens;
    int frameCounter;
    Base* box = NULL;
    int superPowerCounter = 0;
    QSoundEffect shipFiringSound;
    int next_instruct;

    // keys
    void keyPressEvent(QKeyEvent* event);
    void mouseMoveEvent(QMouseEvent* event);
    void mousePressEvent(QMouseEvent* event);

    // about the canvas
    int frames = 0;
    const int WIDTH = 800;
    const int HEIGHT = 600;
    int SCALEDWIDTH;
    int SCALEDHEIGHT;

    // collision...
    int get_collided_swarm(Bullet*& b, AlienBase*& root);
    int get_collided(Bullet*& b, AlienBase*& root);
    void addBullets(const QList<Bullet*>& list);

    // pausing & menu
    bool paused;
    bool gameOver;
    int level;
    int levelLabelCounter = 0;
    void pauseStart();
    Menu* menu;

    // score
    int gameScore;  // this run's score.
public slots:
    void nextFrame();
    // menus
    void showScore();
    void setMouse();
    void setKeyboard();
    void hideMenu();
    void showControls();
    void showSpeeds();
    void setLowSpeed();
    void setMediumSpeed();
    void setHighSpeed();
};
}
