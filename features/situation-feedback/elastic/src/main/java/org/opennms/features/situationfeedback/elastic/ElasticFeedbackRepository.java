/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2018-2018 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2018 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/
package org.opennms.features.situationfeedback.elastic;

import java.util.Collection;
import org.opennms.features.situationfeedback.api.AlarmFeedback;
import org.opennms.features.situationfeedback.api.FeedbackException;
import org.opennms.features.situationfeedback.api.FeedbackRepository;

public class ElasticFeedbackRepository implements FeedbackRepository {

    private final ElasticFeedbackRepositoryInitializer initializer;

    public ElasticFeedbackRepository(ElasticFeedbackRepositoryInitializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public void persist(Collection<AlarmFeedback> feedback) throws FeedbackException {
        ensureInitialized();
        // TODO - persist
    }

    @Override
    public Collection<AlarmFeedback> getFeedback(String situationKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<AlarmFeedback> getFeedback(String situationKey, String situationFingerprint) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<AlarmFeedback> getFeedback(String situationKey, Collection<String> alarmKeys) {
        // TODO Auto-generated method stub
        return null;
    }

    private void ensureInitialized() {
        if (!initializer.isInitialized()) {
            initializer.initialize();
        }
    }
}
