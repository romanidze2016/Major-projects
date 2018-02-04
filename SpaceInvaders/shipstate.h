#ifndef SHIPSTATE_H
#define SHIPSTATE_H

#include <QList>
#include "bulletbuilder.h"
#include "bullet.h"

namespace game {

class ShipState
{
public:
    ShipState() {}
    virtual ~ShipState() {}

    virtual QList<Bullet*> handleShoot(BulletBuilder builder) = 0;
    virtual const QPixmap& get_image() = 0;
};

}

#endif // SHIPSTATE_H
