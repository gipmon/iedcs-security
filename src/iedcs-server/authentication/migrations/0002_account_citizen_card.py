# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='account',
            name='citizen_card',
            field=models.FileField(default='', upload_to=b'citizen_pub_certs'),
            preserve_default=False,
        ),
    ]
