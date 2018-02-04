#ifndef NORMALSHIPSTATE_H
#define NORMALSHIPSTATE_H

#include "shipstate.h"

namespace game {

class NormalShipState : public ShipState
{
public:
    NormalShipState(QPixmap img);
    virtual ~NormalShipState();

    QList<Bullet*> handleShoot(BulletBuilder builder);
    const QPixmap& get_image();
private:
    QPixmap image;
};

}

#endif // NORMALSHIPSTATE_H
