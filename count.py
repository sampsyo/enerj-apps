#!/usr/bin/env python
import os
import json

from collect import shell

BMARKS = {
    'FFT':        ('scimark2',  'jnt/scimark2/FFT.java'),
    'LU':         ('scimark2',  'jnt/scimark2/LU.java'),
    'SOR':        ('scimark2',  'jnt/scimark2/SOR.java'),
    'MonteCarlo': ('scimark2',  'jnt/scimark2/MonteCarlo.java'),
    'SMM':        ('scimark2',  'jnt/scimark2/SparseCompRow.java'),
    'ZXing':      ('zxing',     'core/src javase/src'),
    'jME':        ('jmeint',    'src'),
    'ImageJ':     ('imagefill', 'src'),
    'Plane':      ('simpleRaytracer', 'Plane.java'),
}
COUNT = '../enerj/bin/enerjcount'
ENDPAT = r'Endorsements\.endorse'
SCLC = 'sclc'
SCLC_ARGS = '-sections Totals'

if __name__ == '__main__':
    basedir = os.getcwd()
    
    out = {}
    for name, (projdir, srcdirs) in BMARKS.iteritems():
        os.chdir(projdir)
        sources = shell(r'find %s -name \*.java' % srcdirs)
        sources = ' '.join(sources.split('\n'))
        
        script = os.path.join(basedir, COUNT)
        loccounts = shell('%s %s' % (script, sources))
        
        ends = shell('grep "%s" %s | wc -l' % (ENDPAT, sources))
        
        sclc = 'perl %s %s' % (os.path.join(basedir, SCLC), SCLC_ARGS)
        lines = shell('%s %s' % (sclc, sources))
        
        os.chdir(basedir)
        
        locations, annotations = loccounts.split()
        locations, annotations = int(locations), int(annotations)
        out[name] = {
            'locations': int(locations),
            'annotations': int(annotations),
            'endorsements': int(ends.strip()),
            'loc': int(lines.split()[0]),
        }
    
    print json.dumps(out)
