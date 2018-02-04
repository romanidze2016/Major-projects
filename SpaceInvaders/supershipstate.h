#ifndef SUPERSHIPSTATE_H
#define SUPERSHIPSTATE_H

#include "shipstate.h"

namespace game {

class SuperShipState : public ShipState
{
public:
    SuperShipState(QPixmap img);
    virtual ~SuperShipState();

    QList<Bullet*> handleShoot(BulletBuilder builder);
    const QPixmap& get_image();
private:
    QPixmap image;
};

}

#endif // SUPERSHIPSTATE_H
