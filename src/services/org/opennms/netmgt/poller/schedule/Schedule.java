//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2004-2005 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
// OpenNMS Licensing       <license@opennms.org>
//     http://www.opennms.org/
//     http://www.opennms.com/
//
package org.opennms.netmgt.poller.schedule;


/**
 * Represents a Schedule 
 *
 * @author brozow
 */
public class Schedule {

    private Runnable m_schedulable;
    private ScheduleInterval m_interval;
    private ScheduleTimer m_timer;
    private int m_currentExpirationCode;
    private long m_currentInterval;
    private boolean m_scheduled = false;
    
    class ScheduleEntry implements Runnable {
        private int m_expirationCode;

        public ScheduleEntry(int expirationCode) {
            m_expirationCode = expirationCode;
        }
        
        /**
         * @return
         */
        private boolean isExpired() {
            return m_expirationCode < m_currentExpirationCode;
        }

        public void run() {
            if (isExpired()) return;
            
            if (!m_interval.scheduledSuspension())
                Schedule.this.run();

            // if it is expired by the current run then don't reschedule
            if (isExpired()) return;
            
            long interval = m_interval.getInterval();
            if (interval >= 0 && m_scheduled)
                m_timer.schedule(this, interval);

        }
        
        public String toString() { return "ScheduleEntry for "+m_schedulable; }
    }

    /**
     * @param interval
     * @param timer
     * @param m_schedulable
     * 
     */
    public Schedule(Runnable schedulable, ScheduleInterval interval, ScheduleTimer timer) {
        m_schedulable = schedulable;
        m_interval = interval;
        m_timer = timer;
        m_currentExpirationCode = 0;
    }

    /**
     * 
     */
    public void schedule() {
        m_scheduled = true;
        schedule(0);
    }

    private void schedule(long interval) {
        if (interval >= 0 && m_scheduled)
            m_timer.schedule(new ScheduleEntry(++m_currentExpirationCode), interval);
    }

    /**
     * 
     */
    public void run() {
        m_schedulable.run();
    }

    /**
     * 
     */
    public void adjustSchedule() {
        schedule(m_interval.getInterval());
    }

    /**
     * 
     */
    public void unschedule() {
        m_scheduled = false;
        m_currentExpirationCode++;
    }

}
