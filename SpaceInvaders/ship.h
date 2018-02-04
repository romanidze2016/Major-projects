#pragma once
#include "base.h"
#include "bullet.h"
#include "bulletbuilder.h"
#include "normalshipstate.h"
#include "supershipstate.h"
#include <QPixmap>

namespace game {

class Ship : public Base {
    // A SHIP CAN MOVE LEFT, RIGHT AND SHOOT (ALL WITH AN VELOCITY)

private:
    int velocity;
    int bullet_velocity;
    BulletBuilder builder;
    ShipState* currentState;
    ShipState* superState;
    ShipState* normalState;

public:
    Ship(QPixmap image, double scale, int x, int y);
    QList<Bullet*> shoot();
    void move_left();
    void move_right();
    void setNormalState();
    void setSuperState();
    const QPixmap& get_ship_image();

    virtual ~Ship();
};
}
